package measure.bayes;
import java.util.*;
public class Classifier {   
       
       
    /**  
     *   
     * key   : 类别  
     * value :   
     * key  : 单词  
     * value: 出现次数  
     */  
    private static HashMap< String,HashMap< String,Integer> > typeMap = 
                          new HashMap < String,HashMap< String,Integer>>();   
       
    //总共有多少种word   
    private static Set< String> wordSet = new HashSet< String>();   
       
    //存放每个类别中总共有多少单词   
    HashMap< String,Integer>  sumInType = new HashMap< String,Integer>();   
       
       
    //训练   
    public void train(String[] words,String type)   
    {   
           
        if(typeMap.containsKey(type))   
        {   
            setWordInfo(typeMap.get(type),words,type);   
        }   
        //如果没有此类别   
        else  
        {   
            setWordInfo(new HashMap< String,Integer>(),words,type);   
        }   
           
           
        for( String tp : typeMap.keySet())   
        {   
            int sum = 0;   
            for(String w : typeMap.get(tp).keySet())   
            {   
                sum += 1 *  typeMap.get(tp).get(w);   
            }   
            sumInType.put(tp, sum);   
        }   
           
    }   
       
    Integer getMap(HashMap<String, Integer> map,String key){
    	Integer value = 0;
    	if(map.get(key)!=null){
    		value = map.get(key);
    	}
    	return value;
    }
       
    /**  
     * 计算每个单词的概率和保存单词的出现次数  
     * @param words  
     */  
    private void  setWordInfo(HashMap< String,Integer> childMap,String[] words,String type)   
    {   
        //WordInfo wi ;   
        int sumWord = 0 ;   
        for(String w : words)   
        {   
            if(!childMap.containsKey(w))   
            {   
                childMap.put(w, 1);   
            }   
            else  
            {   
                childMap.put(w, childMap.get(w)+1);   
            }   
        }   
        setWordSet(words);   
        typeMap.put(type, childMap);   
    }   
       
       
    private void setWordSet(String[] words)   
    {   
        for( String w : words)   
        {   
            wordSet.add(w);   
        }   
    }   
       
    public String getClassification(String[] ws)   
    {   
           
        //存放每个   
        HashMap< String,HashMap< String,Double>> mp = new HashMap< String,HashMap< String,Double>>();   
           
        for( String type : typeMap.keySet())   
        {   
            for(String w : ws)   
            {   
                    if(mp.containsKey(type))   
                    {   
                        if(mp.get(type).containsKey(w))   
                            break;   
                        else
                        {   
                            //double p = (double)(typeMap.get(type).get(w)+ 1) / (double)(  wordSet.size() + sumInType.get(type));   
                            double p = (double)(getMap(typeMap.get(type),w)+ 1) / (double)(  wordSet.size() + sumInType.get(type)); 
                            /*      System.out.println("("+ typeMap.get(type).get(w) +
                                 "+ 1)/(" + wordSet.size() + "+ "+sumInType.get(type)+")");  
                            System.out.println("type["+type+"]  "+ w +" p = " + p);  
                            System.out.println("-------------------------------------------");*/  
                            mp.get(type).put(w, p);   
                        }   
                    }   
                    else  
                    {   
                        HashMap< String,Double> submp = new HashMap< String,Double>();   
                        int app = 0;   
                        if(typeMap.get(type).containsKey(w))   
                            app =   typeMap.get(type).get(w);   
                           
                           
                        double p = (double)(app+ 1) / (double)(  wordSet.size() + sumInType.get(type));   
                    /*  System.out.println("("+ app + "+ 1)/(" + wordSet.size() + "+ "+sumInType.get(type)+")");  
                        System.out.println("type["+type+"]  "+ w +" p = " + p);  
                        System.out.println("-------------------------------------------");*/  
                        submp.put(w, p);   
                        mp.put(type, submp);   
                    }   
            }   
        }   
           
        HashMap< String,Integer> time = new HashMap< String,Integer>();    
        for(String w : ws)   
        {   
            if(time.containsKey(w))   
            {   
                time.put(w, time.get(w)+1);   
            }   
            else  
            {   
                time.put(w, 1);   
            }   
        }   
           
        String realType = null;
        double maxP = 0;
           
        for(String ty : mp.keySet())   
        {   
            double p1 = 1;   
            for(String w : mp.get(ty).keySet())   
            {   
                 p1 = p1 * Math.pow((mp.get(ty).get(w)),time.get(w));   
           
            }   
            double p2 = 1d / mp.size() * p1;    
            if (p2 > maxP)   
            {
                maxP = p2;
                realType = ty;   
            }   
        }   
        return realType;   
    }   
       
       
    public static void main(String[] args) {   
           
        String[] s1 = {"Chinese","BeiJing","Chinese"};
        String[] s2 = {"Chinese","Chinese","ShangHai","ShangHai1","ShangHai"};
        String[] s3 = {"Chinese","Japan","Tokyo"};
        String[] s4 = {"Tokyo","Kyoto","Chinese"};   
           
        Classifier cf = new Classifier();
           
        //cf.train(s1, "Y");
        //cf.train(s2, "Y");
        //cf.train(s3, "N");
        //cf.train(s4, "N");
        
        cf.train(s1, "1");
        cf.train(s2, "2");
        cf.train(s3, "3");
        cf.train(s4, "4");
           
        String[] s5 = {"Tokyo","Chinese","Tokyo","Japan"};
        System.out.println("get type = "+cf.getClassification(s5));
    }   
       
       
       
}