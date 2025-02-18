internal class Program
{
    public static void Main(string[] args)
    {
        var lines = File.ReadAllLines("/Users/davidbetteridge/BankOCR/BankOCR/sample.txt");

        for (var lineNumber = 0; lineNumber < lines.Length; lineNumber+=4)
        {
            var line = ProcessLine(lines[lineNumber..(lineNumber + 3)]);
            Console.WriteLine(line);
        }
    }

    private static readonly Dictionary<int, byte> Mappings = new()
    {
        [9] = 1,
        [158] = 2,
        [155] = 3,
        [57] = 4,
        [179] = 5,
        [183] = 6,
        [137] = 7,
        [191] = 8,
        [187] = 9,
        [175] = 0
    };
    
    private static string ProcessLine(string[] lines)
    {
        var result = "";
        var checksum = 0;
        var multiplier = 9;
        var noMatchCount = 0;
        var unmappedBitMap = 0;
        for (var startChar = 0; startChar < lines[0].Length; startChar += 3)
        {
            var bitmap = 0;
            foreach (var line in lines)
            {
                bitmap <<= 1;
                bitmap += line[startChar] == ' ' ? 0 : 1;

                bitmap <<= 1;
                bitmap += line[startChar + 1] == ' ' ? 0 : 1;

                bitmap <<= 1;
                bitmap += line[startChar + 2] == ' ' ? 0 : 1;
            }

            if (Mappings.TryGetValue(bitmap, out var digit))
            {
                result += digit.ToString();
                checksum += digit * multiplier;
            }
            else
            {
                result += '?';
                noMatchCount++;
                unmappedBitMap = bitmap;
            }
            multiplier--;
        }

        var validChecksum = (checksum % 11) == 0;
        var status = noMatchCount > 0 ? "ILL" : (!validChecksum ? "ERR" : "");
        
        if (noMatchCount == 1)
        {
            var solutions = TryFixingUnknownDigit(unmappedBitMap, result.IndexOf('?'), checksum);
            if (solutions.Count == 1)
            {
                result = result.Replace("?", solutions[0].ToString());
                status = " fixed :-)";
            }
            else if (solutions.Count > 1)
            {
                status = "AMB";
            }
        }
        else if (noMatchCount == 0 && !validChecksum)
        {
            var solutions = TryChangingAllDigits(lines);
            if (solutions.Count == 1)
            {
                result = solutions[0];
                status = " fixed :-)";
            }
            else if (solutions.Count > 1)
            {
                status = $"AMB ['{string.Join("', '", solutions)}']";
            }
        }
        
        return $"{result} {status}";
    }

    private static List<string> TryChangingAllDigits(string[] lines)
    {
        // Start by generating all the bitmaps for the read digits
        var bitmaps = new List<int>();
        for (var startChar = 0; startChar < lines[0].Length; startChar += 3)
        {
            var bitmap = 0;
            foreach (var line in lines)
            {
                bitmap <<= 1;
                bitmap += line[startChar] == ' ' ? 0 : 1;

                bitmap <<= 1;
                bitmap += line[startChar + 1] == ' ' ? 0 : 1;

                bitmap <<= 1;
                bitmap += line[startChar + 2] == ' ' ? 0 : 1;
            }
            bitmaps.Add(bitmap);
        }

        var possibleSolutions = new List<string>();
        for (var i = 0; i < bitmaps.Count; i++)
        {
            // Generate all the alternative digits for this location
            foreach (var alternative in Mappings)
            {
                var masked = alternative.Key ^ bitmaps[i];
                if (int.PopCount(masked) == 1)
                {
                    // This is a possible alternative digit - calculate the checksum using this digit
                    var checksum = 0;
                    var multiplier = 9;
                    var solution = "";
                    for (var j = 0; j < bitmaps.Count; j++)
                    {
                        if (j != i)
                        {
                            checksum += Mappings[bitmaps[j]] * multiplier;
                            solution += Mappings[bitmaps[j]];
                        }
                        else
                        {
                            checksum += alternative.Value * multiplier;
                            solution += alternative.Value;
                        }

                        
                        multiplier--;
                    }
                    if (checksum % 11 == 0)
                        possibleSolutions.Add(solution);
                }
            }
        }

        return possibleSolutions;
    }

    /// <summary>
    /// A single digit could not be read,  is there a single digit we can convert this to, such that the
    /// checksum will be valid.
    /// </summary>
    /// <param name="unmappedBitMap">The bitmap of the character without a match</param>
    /// <param name="indexOfDigitToFix">the 0th indexed position</param>
    /// <param name="baseChecksum">the checksum without the broken digit and not mod-ed</param>
    /// <returns>A list of valid replacement digits</returns>
    private static List<byte> TryFixingUnknownDigit(int unmappedBitMap, int indexOfDigitToFix, int baseChecksum)
    {
        var possibleSolutions = new List<byte>();
        foreach (var alternative in Mappings)
        {
            var masked = alternative.Key ^ unmappedBitMap;
            if (int.PopCount(masked) == 1)
            {
                var checksum = (baseChecksum + (alternative.Value * (9 - indexOfDigitToFix))) % 11;
                if (checksum == 0)
                    possibleSolutions.Add(alternative.Value);
            }
        }

        return possibleSolutions;
    }
}