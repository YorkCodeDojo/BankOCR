using System.Text;

namespace BankOCR;

public record Digit(string RawString)
{
    public char Value =>
        _numbers.GetValueOrDefault(RawString, '?');

    public IReadOnlyList<char> PossibleValues
    {
        get
        {
            var values = new List<char>();
            foreach (var (key, va) in _numbers)
            {
                var count = RawString.Zip(key, (a, b) => (a, b))
                    .Count(x => x.a != x.b);

                if (count <= 1)
                {
                    values.Add(va);
                }
            }

            return values;
        }
    }


    public static IReadOnlyList<Digit> FromRawString(string input)
    {
        var map = _numbers.ToDictionary(x => x.Value, pair => pair.Key);
       
        return input.Select(x => new Digit(map.GetValueOrDefault(x, "?")))
            .ToArray();
    }

    private static Dictionary<string, char> _numbers = new()
    {
        {
            """
               
              |
              |
            """,
            '1'
        },
        {
            """
             _ 
             _|
            |_ 
            """,
            '2'
        },
        {
            """
             _ 
             _|
             _|
            """,
            '3'
        },
        {
            """
               
            |_|
              |
            """,
            '4'
        },
        {
            """
             _ 
            |_ 
             _|
            """,
            '5'
        },
        {
            """
             _ 
            |_ 
            |_|
            """,
            '6'
        },
        {
            """
             _ 
              |
              |
            """,
            '7'
        },
        {
            """
             _ 
            |_|
            |_|
            """,
            '8'
        },
        {
            """
             _ 
            |_|
             _|
            """,
            '9'
        },
        {
            """
             _ 
            | |
            |_|
            """,
            '0'
        }
    };

    public bool Invalid() => RawString.Contains('?');
}

public record BankAccountNumber(IReadOnlyList<Digit> AccountNumber)
{
    public bool IsValidate()
    {
        var total = AccountNumber
            .Select(x => x.Value)
            .Select(x => (int)char.GetNumericValue(x))
            .Reverse()
            .Select((number, index) => number * (index + 1))
            .Sum();

        return total % 11 == 0;
    }

    public static BankAccountNumber Parse(string text)
    {
        var lines = text.Split(Environment.NewLine)
            .Take(3)
            .ToArray();

        List<Digit> parsed = new();
        for (var i = 0; i < lines[0].Length; i += 3)
        {
            var line1 = lines[0].Substring(i, 3);
            var line2 = lines[1].Substring(i, 3);
            var line3 = lines[2].Substring(i, 3);
            var singleChar = string.Join(Environment.NewLine, line1, line2, line3);

            parsed.Add(new Digit(singleChar));
        }

        return new(parsed);
    }

    public string ToAccountNumberString() =>
        string.Join("", AccountNumber.Select(x => x.Value));

    public string ToResult()
    {
        if (AccountNumber.Any(x => x.Invalid()))
        {
            return ToAccountNumberString() + " ILL";
        }

        if (!IsValidate())
        {
            return ToAccountNumberString() + " ERR";
        }

        return ToAccountNumberString();
    }
}

public class BankOCRTests
{
    public static TheoryData<string, string> SingleCharData =>
        new()
        {
            {
                """
                   
                  |
                  |
                  
                """,
                "1"
            },
            {
                """
                 _ 
                 _|
                |_ 
                  
                """,
                "2"
            },
            {
                """
                 _ 
                 _|
                 _|
                   
                """,
                "3"
            },
            {
                """
                   
                |_|
                  |
                   
                """,
                "4"
            },
            {
                """
                 _ 
                |_ 
                 _|
                   
                """,
                "5"
            },
            {
                """
                 _ 
                |_ 
                |_|
                   
                """,
                "6"
            },
            {
                """
                 _ 
                  |
                  |
                   
                """,
                "7"
            },
            {
                """
                 _ 
                |_|
                |_|
                   
                """,
                "8"
            },
            {
                """
                 _ 
                |_|
                 _|
                   
                """,
                "9"
            },
            {
                """
                 _ 
                | |
                |_|
                   
                """,
                "0"
            }
        };

    [Theory]
    [MemberData(nameof(SingleCharData))]
    public void ShouldParseSingleChar(string text, string expectation)
    {
        var account = BankAccountNumber.Parse(text);

        Assert.Equal(expectation, account.ToAccountNumberString());
    }

    public static TheoryData<string, string> MultipleCharData =>
        new()
        {
            {
                """
                    _ 
                  | _|
                  ||_ 
                      
                """,
                "12"
            },
            {
                """
                    _  _     _  _  _  _  _  _ 
                  | _| _||_||_ |_   ||_||_|| |
                  ||_  _|  | _||_|  ||_| _||_|
                 
                """,
                "1234567890"
            }
        };

    [Theory]
    [MemberData(nameof(MultipleCharData))]
    public void ShouldParseMultipleChar(string text, string expectation)
    {
        var account = BankAccountNumber.Parse(text);

        Assert.Equal(expectation, account.ToAccountNumberString());
    }

    [Fact]
    public void ShouldParseInvalidCharsAsQuestionMark()
    {
        var text = """
                       _  _     _  _  _  _  _ 
                     | _| _||_| _||_   ||_||_|
                     ||_  _|  | _||_|  ||_| _ 
                   """;
        var account = BankAccountNumber.Parse(text);

        Assert.Equal("12343678?", account.ToAccountNumberString());
    }

    [Fact]
    public void ShouldValidateAccountNumber()
    {
        var account = new BankAccountNumber(Digit.FromRawString("345882865"));
        var isValid = account.IsValidate();

        Assert.True(isValid);
    }

    [Fact]
    public void ShouldValidateAccountNumberAndFail()
    {
        var account = new BankAccountNumber(Digit.FromRawString("223456789"));
        var isValid = account.IsValidate();

        Assert.False(isValid);
    }

    [Fact]
    public void ShouldOutputResultWithErrorIfChecksumDoesNotMatch()
    {
        var account = new BankAccountNumber(Digit.FromRawString("223456789"));

        Assert.Equal("223456789 ERR", account.ToResult());
    }

    [Fact]
    public void ShouldOutputResultWithInvalidForInvalidChar()
    {
        var account = new BankAccountNumber(Digit.FromRawString("2234?6789"));
        Assert.Equal("2234?6789 ILL", account.ToResult());
    }
}

public class DigitTests
{
    [Fact]
    public void DigitShouldHavePossibleValues()
    {
        var digitString = """
                            
                           |
                           |
                         """;

        var digit = new Digit(digitString);
        
        Assert.True(digit.PossibleValues.SequenceEqual(['1', '7']));
    }
}