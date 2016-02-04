public class MatcherNew {
    public MatcherNew() {}
   
    public boolean match(int[] expected, int[] actual, 
        int clipLimit, int delta) 
    {
       for (int i = 0; actual.length; i++)
       {
           if (actual[i] > cliplimit)
           actual[i] = cliplimit;
           
           if (acutal.length != expected.length) || (Math.abs(expected[i] - acutal[i] > delta))
           return false;
       }
       return true;
    }
}