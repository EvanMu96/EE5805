
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Tut_03 
{
    public static void main(String[] args) // DO NOT modify the main() method
    {
        String fname = "videoData.txt";        
        ArrayList<VideoRec> list = readDataFile(fname);        
        
        System.out.println("Top 10 most popular videos (Video ID, view count):");
        
        List<Pair<String, Integer>> top10 = findTop10Video(list);

        for (Pair<String, Integer> p : top10)
            System.out.println(p);        
        
        /* Expected output:
        Top 10 most popular videos (Video ID, view count):
        (SEknH0jt, 356)
        (m6fTBdej, 355)
        (t0H9hlqb, 346)
        (qWc0uJ4K, 337)
        (OOEmaeFm, 327)
        (n9IofCpB, 317)
        (PueHJlUX, 298)
        (RJL2Ncb3, 277)
        (HX3a94A6, 252)
        (OIV4FjWb, 251)
        */
    }
    
    private static ArrayList<VideoRec> readDataFile(String fname)
    {
        // Read in the VideoRec from data file
        ArrayList<VideoRec> list = new ArrayList();
        
        try (Scanner sc = new Scanner(new File(fname)))
        {
            while (sc.hasNextLine())
            {
                String line = sc.nextLine();
                String[] token = line.split(",");
                list.add(new VideoRec(Long.parseLong(token[0]), token[1], token[2]));   
            }
        }
        catch(FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }         
        return list;
    }    
    
    private static List<Pair<String, Integer>> findTop10Video(ArrayList<VideoRec> list)
    {
        list.sort(new Comparator<VideoRec>() {
                      @Override
                      public int compare(VideoRec r1, VideoRec r2)
                      {
                          return r1.getVid().compareTo(r2.getVid());
                      }
                  });
        
        // Code statement using Lambda expression:
        // list.sort((r1, r2)-> r1.getVid().compareTo(r2.getVid()));        
        // list.sort(comparing(VideoRec::getVid));        
        
        ArrayList<Pair<String, Integer>> viewCountList = new ArrayList(); 
        
        int i = 0;
        while (i < list.size())
        {
            String curVid = list.get(i).getVid();
            int j = i + 1;
            while (j < list.size() && list.get(j).getVid().equals(curVid))
                j++;
            
            viewCountList.add(new Pair(curVid, j-i));
            i = j;
        }
        
        // viewCountList.sort((a, b) -> b.getSecond() - a.getSecond());
        viewCountList.sort(new Comparator<Pair<String, Integer>>() {
                               @Override
                               public int compare(Pair<String, Integer> a, Pair<String, Integer> b)
                               {
                                   return b.getSecond() - a.getSecond();
                               }
                           });

        if (viewCountList.size() >= 10)
            return viewCountList.subList(0, 10);
        else
            return viewCountList;
    }
}

