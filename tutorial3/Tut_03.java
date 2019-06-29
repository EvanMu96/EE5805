// Student name: MU Sen
// Student ID  : 55364121

// Submission deadline: Friday, 28 June, 4 pm

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Iterator;

public class Tut_03 
{
    public static void main(String[] args) // DO NOT modify the main() method
    {
        String fname = "videoData.txt";        
        ArrayList<VideoRec> list = readDataFile(fname);        
        
        System.out.println("Top 10 most popular videos (Video ID, view count):");
        //System.out.println(list);
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
                //System.out.print(token);
                list.add(new VideoRec(Long.parseLong(token[0]), token[1], token[2]));   
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }         
        return list;
    }    
    
    private static List<Pair<String, Integer>> findTop10Video(ArrayList<VideoRec> list)
    {
                
        // Your codes.
        //System.out.print(list);
        // Sort the input list by vid, and then count the number of times each vid is viewed.
        // Return a List<Pair<String, Integer>> with up to 10 records (video ID, viewCount).
        Comparator<VideoRec> byVid = new Comparator<VideoRec>() {
		@Override
		public int compare(VideoRec o1, VideoRec o2) {
			if (o1.getVid() == o2.getVid())
                            return 0;
                        else
                            return o1.getVid().compareTo(o2.getVid());
		}
	};
        list.sort(byVid);
        class PairComparator implements Comparator<Pair<String, Integer>>{
            @Override
            public int compare(Pair<String, Integer> p1, Pair<String, Integer> p2){
                if (p1.getSecond() == p2.getSecond())
                    return 0;
                else if(p1.getSecond() > p2.getSecond())
                    return -1;
                else
                    return 1;
            }
        }
            
        PairComparator byCount = new PairComparator();
        //System.out.println(list);
        
        ArrayList<Pair<String, Integer>> result = new ArrayList<Pair<String, Integer>>();
        int count = 1;
        int total_count = 0;
        Iterator<VideoRec> i = list.iterator();
        VideoRec prev = i.next();
        while(i.hasNext()){
            VideoRec cur = i.next();
            if(prev.getVid().equals(cur.getVid())){
                count++;
                prev = cur;
            }
            else{
                //System.out.println(prev.getVid() + cur.getVid());
                //System.out.println("added!");
                Pair<String, Integer> nP = new Pair(prev.getVid(), count);
                result.add(nP);
                count = 1;
                prev = cur;
                total_count++;
            }
            
        }
        result.sort(byCount);
        
        // List is an interface.
        // ArrayList is a concrete class that implements the List interface.
        System.out.println(result.size());
        return result.subList(0, 10);
        // return result;  // dummy return statement
    }    
}

