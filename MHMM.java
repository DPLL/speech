import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;

 
public class MHMM
{
/*	static final String HEALTHY = "Healthy";
	static final String FEVER = "Fever";
 
    static final String CUT = "cut";
    static final String HAT = "hat";
    static final String MAD = "mad";

	//added actual observatinons of vocabularySet
    static final String MAC = "mac";
    static final String HIT = "hit";
    static final String CAT = "cat";*/
	
	static final String ZERO  = "0";
	static final String ONE   = "1";
	static final String TWO   = "2";
	static final String THREE = "3";
	static final String FOUR  = "4";
	
    static final String OXYG = "oxygen";
    static final String DEFI = "defibrillator";
    static final String FLAT = "flat";
    static final String ASYS = "asystole";
    static final String SHOC = "shock";
    static final String ELEC = "electric";
    static final String PUSH = "push";
    static final String INTR = "intravenous";
    static final String EPI  = "epinephrine";
    static final String AMIO = "amiodarone";
    
	//added actual observatinons of vocabularySet
    static final String oxyg = "oxygen";
    static final String asys = "assistant";
    static final String elec = "trick";
    static final String epi = "epinephrine";
    static final String darrell = "still";
    //static final String darrell = "zen zen zen zen zen zen zen zen zen zen";
    static final String later = "maybe later";
/*    static final String oxyg = "oxygen";
    static final String asys = "asystole";
    static final String elec = "electric";
    static final String epi = "epinephrine";*/

	
    
    static final int port = 9998;
 
    public static void main(String[] args) throws IOException, InterruptedException 
    {
    	//String[] states = new String[] {HEALTHY, FEVER};
    	String[] states = new String[] {ZERO, ONE, TWO, THREE, FOUR};
 
        //String[] accurate vocabularySet 
    	//String[] actualVocabularySet = new String[] {MAC, HIT, CAT};
        String[] vocabularySet = new String[] {OXYG, DEFI, FLAT, ASYS, SHOC, ELEC, PUSH, INTR, EPI, AMIO};

		//String[] inaccurate vocabularySet 
		//String[] actualVocabularySet = new String[] {MAC, HIT, CAT};
        //String[] actualVocabularySet = new String[] {oxyg, asys, elec, epi};
        String[] actualVocabularySet = new String[] {darrell, later};
 
        Hashtable<String, Double> start_probability = new Hashtable<String, Double>();
        /*
        start_probability.put(HEALTHY, 0.6d);
        start_probability.put(FEVER, 0.4d);*/
        
        start_probability.put(ZERO, 0.2d);
        start_probability.put(ONE, 0.2d);
        start_probability.put(TWO, 0.2d);
        start_probability.put(THREE, 0.2d);
        start_probability.put(FOUR, 0.2d);
 
/*        // transition_probability
        Hashtable<String, Hashtable<String, Double>> transition_probability = 
        		new Hashtable<String, Hashtable<String, Double>>();
        Hashtable<String, Double> t1 = new Hashtable<String, Double>();
        t1.put(HEALTHY, 0.7d);
        t1.put(FEVER, 0.3d);
        Hashtable<String, Double> t2 = new Hashtable<String, Double>();
        t2.put(HEALTHY, 0.4d);
        t2.put(FEVER, 0.6d);
        transition_probability.put(HEALTHY, t1);
        transition_probability.put(FEVER, t2);
 
        // emission_probability
        Hashtable<String, Hashtable<String, Double>> emission_probability = 
        		new Hashtable<String, Hashtable<String, Double>>();
        Hashtable<String, Double> e1 = new Hashtable<String, Double>();
        e1.put(CUT, 0.1d);            
        e1.put(HAT, 0.4d); 
        e1.put(MAD, 0.5d);
        Hashtable<String, Double> e2 = new Hashtable<String, Double>();
        e2.put(CUT, 0.6d);            
        e2.put(HAT, 0.3d); 
        e2.put(MAD, 0.1d);
        emission_probability.put(HEALTHY, e1);
        emission_probability.put(FEVER, e2);*/

        // transition_probability
        Hashtable<String, Hashtable<String, Double>> transition_probability = 
        		new Hashtable<String, Hashtable<String, Double>>();
        Hashtable<String, Double> t0 = new Hashtable<String, Double>();
        t0.put(ZERO, (1.0f/3.0d));
        t0.put(ONE, (1.0f/3.0d));
        t0.put(TWO, (1.0f/3.0d));
        Hashtable<String, Double> t1 = new Hashtable<String, Double>();
        t1.put(ONE, 0.5d);
        t1.put(TWO, 0.5d);
        Hashtable<String, Double> t2 = new Hashtable<String, Double>();
        t2.put(TWO, (1.0f/3.0d));
        t2.put(THREE, (1.0f/3.0d));
        t2.put(FOUR, (1.0f/3.0d));
        transition_probability.put(ZERO, t0);
        transition_probability.put(ONE, t1);
        transition_probability.put(TWO, t2);
 
        // emission_probability
        Hashtable<String, Hashtable<String, Double>> emission_probability = 
        		new Hashtable<String, Hashtable<String, Double>>();
        Hashtable<String, Double> e0 = new Hashtable<String, Double>();
        e0.put(OXYG, 0.5d);            
        e0.put(DEFI, 0.5d);
        Hashtable<String, Double> e1 = new Hashtable<String, Double>();
        e1.put(FLAT, 0.5d);            
        e1.put(ASYS, 0.5d); 
        Hashtable<String, Double> e2 = new Hashtable<String, Double>();
        e2.put(SHOC, (1.0f/3.0d));            
        e2.put(ELEC, (1.0f/3.0d)); 
        e2.put(OXYG, (1.0f/3.0d));
        Hashtable<String, Double> e3 = new Hashtable<String, Double>();
        e3.put(INTR, (1.0f/3.0d));            
        e3.put(PUSH, (1.0f/3.0d)); 
        e3.put(DEFI, (1.0f/3.0d));
        Hashtable<String, Double> e4 = new Hashtable<String, Double>();
        e4.put(EPI, 0.5d);            
        e4.put(AMIO, 0.5d); 
        emission_probability.put(ZERO, e0);
        emission_probability.put(ONE, e1);
        emission_probability.put(TWO, e2);
        emission_probability.put(THREE, e3);
        emission_probability.put(FOUR, e4);
        
		//confusion_probability
        /*
        // Hardcode confusion_probability
		Hashtable<String, Hashtable<String, Double>> confusion_probability = 
			new Hashtable<String, Hashtable<String, Double>>();
		Hashtable<String, Double> c1 = new Hashtable<String, Double>();
		c1.put(CUT, 0.0f);
		c1.put(HAT, (1.0f/3.0d));
		c1.put(MAD, (2.0f/3.0d));
		Hashtable<String, Double> c2 = new Hashtable<String, Double>();
		c2.put(CUT, (1.0f/3.0d));
		c2.put(HAT, (2.0f/3.0d));
		c2.put(MAD, 0.0f);
		Hashtable<String, Double> c3 = new Hashtable<String, Double>();
		c3.put(CUT, (2.0f/3.0d));
		c3.put(HAT, (2.0f/3.0d));
		c3.put(MAD, (1.0f/3.0d));
		confusion_probability.put(MAC, c1);
		confusion_probability.put(HIT, c2);
		confusion_probability.put(CAT, c3);*/

        
/*        Hashtable<String, Hashtable<String, Double>> confusion_probability =
        		confustionGen(actualVocabularySet, vocabularySet);*/
/*
        System.out.println("LD is " + computeLevenshteinDistance("0ksIdZ@n", "0pS@n"));*/
        
        
        ArrayList<String> strArr = new ArrayList<String>(); 
        
		DatagramSocket serverSocket = new DatagramSocket(port);
        System.out.println("In the UDPserver");

        while(true)
        {
	        byte[] receiveData = new byte[256];
	        //byte[] sendData = new byte[256];
	    	DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	    	serverSocket.receive(receivePacket);
	        //String revStr = new String(receivePacket.getData());
	    	
	    	//Notice that receivePacket.getData() is 256 and receivePacket.getLength() is the actual length
	    	String revStr = new String(receiveData, 0, receivePacket.getLength());
	        //System.out.println("receivePacket.getLength(): " + receivePacket.getLength());
	        //System.out.println("revStr.length(): " + revStr.length());
	        System.out.println("RECEIVED: " + revStr);
	        
	        if(revStr.equals("over"))
	        		break;
	
	        //strArr.add(revStr);
        	//System.out.println("strArr.size() is " + strArr.size());
	        //String[] actualVocSet = strArr.toArray(new String[strArr.size()]);
	        //System.out.println(Arrays.toString(actualVocSet));
	        /*
             * modified for one-time MHMM
             */
            // manipulate the received string 
            // Pay attention to the match case of "|" in java!!!
            String[] wordSeq = revStr.split("\\|"); 
            System.out.println("split.size: " + wordSeq.length);
            System.out.println("The received seq after manipulation is:");
            for (String str : wordSeq) {
                System.out.println(str);
            }
            System.out.println(Arrays.toString(wordSeq));
	        
	        /*Hashtable<String, Hashtable<String, Double>> confusion_probability =
	        		confustionGen(actualVocSet, vocabularySet);*/
	        
	        Hashtable<String, Hashtable<String, Double>> confusion_probability =
	        		confustionGen(wordSeq, vocabularySet);
            /*
	        forward_viterbi(actualVocSet,
	        		vocabularySet, states,
	                start_probability,
	                transition_probability,
	                emission_probability,
	                confusion_probability);*/

	        
	        forward_viterbi(wordSeq,
	        		vocabularySet, states,
	                start_probability,
	                transition_probability,
	                emission_probability,
	                confusion_probability);
        }
        serverSocket.close();
        

/*        
        forward_viterbi(actualVocabularySet,
        		vocabularySet, states,
                start_probability,
                transition_probability,
                emission_probability,
                confusion_probability);*/
        }
 
        public static void forward_viterbi(String[] actualObs, String[] obs, String[] states,
                        Hashtable<String, Double> start_p,
                        Hashtable<String, Hashtable<String, Double>> trans_p,
                        Hashtable<String, Hashtable<String, Double>> emit_p,
			Hashtable<String, Hashtable<String, Double>> conf_p)
        {

        	int state_num = states.length;
        	//int obs_num = obs.length;
        	int obs_num = actualObs.length;
        	//V[t][i] stores the overall largest probability ending at the state of i at time t
        	double V[][] = new double[obs_num+1][state_num];
        	//B[t][i]  stores the last source state corresponding to the V[t][i]
        	int B[][] = new int[obs_num+1][state_num];
        	//X[t][i] stores the word that has been chosen corresponding to the V[t][i]
        	String X[][] = new String[obs_num+1][state_num];
		
        	int m = 0;
        	for (String state : states)
        	{
        		//V[0][m] = start_p.get(state);
                V[0][m] = Math.log(start_p.get(state));
        		B[0][m] = m;
        		X[0][m] = "@";
        		m++;
        	}

        	// t is the records the current time
        	int t = 0;
            for (String input : actualObs)
            {
            	// input is the current actual observation
            	t++;
            	// i is the current state
            	int i = -1;
                for (String next_state : states)
                {
                	i++;
                    int Smax = -1;
                    //double Pmax = 0;
                    double Pmax = Double.NEGATIVE_INFINITY;
                    String v_word = "";	
                    double v_prob = 1;       
			
                    // x is the current accurate observation (word)	
                    int x = -1;
                    for (String word : obs)		
                    {
                    	x++;
                        if (t == 1) {
                            if (emit_p.get(next_state) == null || emit_p.get(next_state).get(word) == null) {
                                v_prob = Double.NEGATIVE_INFINITY;
                            } else {
                                v_prob = Math.log(start_p.get(next_state)) + Math.log(conf_p.get(input).get(word))
                                    + Math.log(emit_p.get(next_state).get(word));
                            }
                            if (v_prob >= Pmax) {
                                Pmax = v_prob;
                                Smax = i;
                                v_word = word;
                            }
                        } else {
                        	// j is the previous state
                        	int j = -1; 
                        	for (String source_state : states)
                        	{
                        		j++;
                        		double p;
                        		if (emit_p.get(next_state) == null || trans_p.get(source_state) == null ||
                        				emit_p.get(next_state).get(word) == null || trans_p.get(source_state).get(next_state) == null)
                        			//p = 0;
                                    p = Double.NEGATIVE_INFINITY;
                        		else
    	                    		//p = emit_p.get(next_state).get(word) * 
    	                    		//		trans_p.get(source_state).get(next_state) * conf_p.get(input).get(word);
     	                    		p = Math.log(emit_p.get(next_state).get(word)) +  
    	                    				Math.log(trans_p.get(source_state).get(next_state)) + Math.log(conf_p.get(input).get(word));
 
                        		//v_prob = V[t-1][j] * p;
                                v_prob= V[t-1][j] + p;
    						
                        		if (v_prob >= Pmax)
                        		{
                        			Pmax = v_prob;
                        			Smax = j;
                        			v_word = word;
                        		}
                        	}
                        }
                    }
                    V[t][i] = Pmax;
                    B[t][i] = Smax;
                    X[t][i] = v_word;
                }
            }
 
            double total = 0;
            String argmax = "";
            double valmax = 0;
 
            double prob;
            String v_path;
            double v_prob;
	
            /*
            for (int n = 0; n < obs_num+1; n++)
            	for (int z = 0; z < state_num; z++)
            		System.out.println(V[n][z]);
            for (int n = 0; n < obs_num+1; n++)
            	for (int z = 0; z < state_num; z++)
            		System.out.println(B[n][z]);
            for (int n = 0; n < obs_num+1; n++) 
            	for (int z = 0; z < state_num; z++)
            		System.out.println(X[n][z]);
			*/
		
            int Smax = -1;
            //double pMax = 0;
            double pMax = Double.NEGATIVE_INFINITY;
            for (int n = 0; n < state_num; n++ ) 
            {
            	if (V[obs_num][n] >= pMax) {
            		pMax = V[obs_num][n];
            		Smax = n;
            	}
            }

            // If at the current stage, the output of ASR is too far-away from the vocabulary set,
            // it needs special handling.
            int path[] = new int[obs_num + 1];
            String words[] = new String[obs_num + 1];
            
            path[obs_num]  = Smax;
            words[obs_num] = X[obs_num][Smax];
            for (int x = obs_num-1; x >= 0; x--)
            {
            	path[x] = B[x+1][path[x+1]];
            	words[x] = X[x][path[x]]; 
            }

            	
            System.out.println("\n*************************************\n");         
            System.out.println(Arrays.toString(actualObs));
            
            for (int x = 0; x < obs_num+1; x++)
            {
            	System.out.println("state: " + path[x] + 
            			", with the word: " + words[x]);
            }
            System.out.println("\n*************************************\n");
        }

		// convolution_index calculates the overlapping similarity of two strings
		// Notice that p_src is the string in the vocabulariy set, and p_dest is the
		// the actual string that is heard.
		public static double convolution_index(String p_src, String p_dest)
    	{
		    int len_src = p_src.length();
		    int len_dest = p_dest.length();
		    int steps = 2*len_src + len_dest;
		    int i,c;
		    double retval = 0;
	   
		    for(i=0; i<steps; ++i) {
		        int cur_conv_index = 0;
		        int start_s = Math.max(0, (len_src - 1 - i));
		        int start_d = Math.max(0, i - len_src + 1);
		        while(start_s < len_src && start_d < len_dest) {
		            //System.out.println(p_src.substring(start_s,start_s+1) + " == " + p_dest.substring(start_d,start_d+1));
		            if(p_src.substring(start_s,start_s+1).equals(p_dest.substring(start_d,start_d+1))) {
		                ++cur_conv_index;
		            }
		            ++start_s;
		            ++start_d;
		        }
		        if(cur_conv_index > retval)
		            retval = cur_conv_index;
		    }
			System.out.println("The convolution index value between " + p_src + " and " + p_dest + " is " + retval);
		    return retval/len_dest;       
		}


        public static Hashtable<String, Hashtable<String, Double>> 
        	confustionGen(String[] obs, String[] vocalbularySet) throws IOException, InterruptedException
        {
        	/*
        	 * convert the word sequence into phonetic sequence by calling eSpeak.
        	 */        	
        	//.out.println(Arrays.toString(obs));
        	//System.out.println(Arrays.toString(vocalbularySet));
        	String[] obsPhonemes;
        	//String[] obsPhonemes = {"anju:@L d'arEl", "m'eIbi: l'eIt3"};
        	String[] vocalPhonemes;
        	
        	obsPhonemes = phonemeConversion(obs);       	
        	vocalPhonemes = phonemeConversion(vocalbularySet);        	
        	
/*        	for (String temp : obsPhonemes)
        		System.out.println(temp);
        	
        	for (String temp : vocalPhonemes)
        		System.out.println(temp);
        	*/
        	  
        	/*
        	 * calculate the Levenshtein distance and generate the corresponding confusion matrix.
        	 */
        	//System.out.println("computeLevenshteinDistance mac and mad "  + computeLevenshteinDistance(obsPhonemes[0], vocalPhonemes[0]));
        	
        	/*
    		Hashtable<String, Double> c3 = new Hashtable<String, Double>();
    		c3.put(CUT, (2.0f/3.0f));
    		c3.put(HAT, (2.0f/3.0f));
    		c3.put(MAD, (1.0f/3.0f));
    		confusion_probability.put(MAC, c1);*/
        	
        	int i = 0;
        	int j;
    		Hashtable<String, Hashtable<String, Double>> confusion_probability = 
    				new Hashtable<String, Hashtable<String, Double>>();
        	for (String obsWord : obs) {
        		Hashtable<String, Double> c = new Hashtable<String, Double>();
        		// get rid of the utility 'phonemes'
				// Notice here that getting rid of space is quite useful, wheraz other utility 
				// phonemes such as # is not so clear.
    			String obsPhoneme = obsPhonemes[i].replaceAll("[',%=_:| ]", "");
        		j = 0;
        		for (String volWord : vocalbularySet) {
        			double similarityIndex;
        			// get rid of the utility 'phonemes'
        			String vocalPhoneme = vocalPhonemes[j].replaceAll("[',%=_:| ]", "");
        			int LDistance = computeLevenshteinDistance(obsPhoneme, vocalPhoneme);
        			//int wordLength = obsWord.length();
        			//int wordLength = obsPhonemes[i].length();
        			int wordLength = obsPhoneme.length();
        			//System.out.println("vocal " + vocalPhonemes[j] + 
        			//		" LDistance " + LDistance + " wordLength " + wordLength);
        			//similarityIndex = ( LDistance <= wordLength ? (1 - ((double)LDistance/wordLength)) : 0);
                    similarityIndex = 1 - ((double)LDistance/(Math.max(obsPhoneme.length(), vocalPhoneme.length())));
					// --------------------------------------------------------
					// Modified for adding convolution index
					double conv = convolution_index(vocalPhoneme, obsPhoneme);
					similarityIndex *= conv;
					// --------------------------------------------------------
        			c.put(volWord, similarityIndex);
        			j++;
        		}
        		confusion_probability.put(obsWord, c);
        		i++;
        	}
        	
        	//System.out.println("Hi, I am here");
        	
        	Enumeration<String> names;	
            names = confusion_probability.keys();
            while(names.hasMoreElements()) {
               String str = (String) names.nextElement();
               System.out.println(str + ": " +
            		   confusion_probability.get(str));
            }
        	
			return confusion_probability;
        }
        
    	public static String[] phonemeConversion(String[] str) throws IOException, InterruptedException
    	{
        	Process p;
        	String speakCall = "speak -x ";
        	String line;
        	int i = 0;
        	
        	String[] phenemeArr = new String[str.length];
        	String command;
    		
        	for (String word : str)
        	{
        		// Probably because of the bug in speak package, it cannot return desired results.
        		String[] subWords = word.split(" ");
        		StringBuilder out = new StringBuilder();
        		int j = 0;
        		for (String subWord : subWords)
        		{
        			j++;
	        		command = speakCall + subWord;
	        		p = Runtime.getRuntime().exec(command);
	            	p.waitFor();
	            	
	            	BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
	            	
	            	// return the subString(1) because of the fact that espeak adds a space at the beginning
	            	//line = reader.readLine().substring(1);
	            	
	            	if ((line = reader.readLine()) != null)
	            	{
	            		//System.out.println(line);
	            		if (j == 1)
	            			out.append(line.substring(1));
	            		else
	            			out.append(" ").append(line.substring(1));
	            	}
	            	phenemeArr[i] = out.toString();
        		}
            	/*
            	StringBuilder out = new StringBuilder();
                String curr = null, previous = null;
                while ((curr = reader.readLine()) != null)
                {
                    if (!curr.equals(previous)) {
                        previous = curr;
                        out.append(curr).append('\n');
                        //System.out.println(curr);
                        phenemeArr[i] = out;
                    }
                }*/           	
            	i++;
        	}
        	
			return phenemeArr;	
    	}
    	
        private static int minimum(int a, int b, int c) {
            return Math.min(Math.min(a, b), c);
        }

        public static int computeLevenshteinDistance(String str1,String str2) {
            int[][] distance = new int[str1.length() + 1][str2.length() + 1];

            for (int i = 0; i <= str1.length(); i++)
            	distance[i][0] = i;
            for (int j = 1; j <= str2.length(); j++)
                distance[0][j] = j;

            for (int i = 1; i <= str1.length(); i++)
                for (int j = 1; j <= str2.length(); j++)
                    distance[i][j] = minimum(
                    		distance[i - 1][j] + 1,
                            distance[i][j - 1] + 1,
                            // TODO change the weight of substuting one character of the word to 2
                            //distance[i - 1][j - 1]+ ((str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : 2));
                            distance[i - 1][j - 1]+ ((str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : 1));

            return distance[str1.length()][str2.length()];    
        }
}

