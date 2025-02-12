using System.Text;

namespace BankOCR;

public class BankAccountNumberParser
{
    private static Dictionary<string, string> _numbers = new()
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

    public static string Parse(string text)
    {
        var lines = text.Split(Environment.NewLine)
            .Take(3)
            .ToArray();

        StringBuilder parsed = new();
        for (var i = 0; i < lines[0].Length; i += 3)
        {
            var line1 = lines[0].Substring(i, 3);
            var line2 = lines[1].Substring(i, 3);
            var line3 = lines[2].Substring(i, 3);
            var singleChar = string.Join(Environment.NewLine, line1, line2, line3);

            parsed.Append(_numbers[singleChar]);
        }

        return parsed.ToString();
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
        var account = BankAccountNumberParser.Parse(text);

        Assert.Equal(expectation, account);
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
        var account = BankAccountNumberParser.Parse(text);

        Assert.Equal(expectation, account);
    }
}