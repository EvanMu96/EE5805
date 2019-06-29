
public class VideoRec  // DO NOT modify this class
{
    private final long timestamp;
    private final String vid;
    private final String client;
    
    public VideoRec(long t, String v, String c)
    {
        timestamp = t;
        vid = v;
        client = c;
    }
    
    public long getTimestamp()
    {
        return timestamp;
    }
    
    public String getVid()
    {
        return vid;
    }
    
    public String getClient()
    {
        return client;
    }
    
    @Override
    public String toString()
    {
        return timestamp + "," + vid + "," + client;
    }
}
