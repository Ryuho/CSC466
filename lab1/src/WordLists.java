import java.util.*;
public class WordLists 
{
	ArrayList<Words> wlist; //wordlist
	ArrayList<ArrayList<Words>> slist; //sentencelist
	int sentenceCount = 0;
	int paragraphCount = 0;
	
	/*Creates word list from raw List, must be used first */
	WordLists(ArrayList<TextToken> lis)
	{
		ArrayList<Words> st = new ArrayList<Words>();
		ArrayList<Words> temp = new ArrayList<Words>();
		slist = new ArrayList<ArrayList<Words>>();
		for(int i = 0; i < lis.size(); i++)
		{
			if(lis.get(i) instanceof Words)
			{
				//check for frequency
				boolean contains =false;
				int containsIndex = 0;
				
				for(int j = 0; j< temp.size(); j++)
				{
					if(lis.get(i).str.equalsIgnoreCase(temp.get(j).str))
					{
						contains = true;
						containsIndex = j;
						break;
					}
				}
				
				if(contains)
				{
					temp.get(containsIndex).addOne(); 
				}
				else
				{
					temp.add((Words) lis.get(i));
				}
				st.add((Words) lis.get(i));
			}
			else if(lis.get(i) instanceof EOS)
			{
				slist.add(st);
				st = new ArrayList<Words>();
				sentenceCount++;
			}
			else if(lis.get(i) instanceof EOP)
			{
				paragraphCount++;
			}
			
		}
		wlist = temp;
	}
	
	/*sort word list by word */
	@SuppressWarnings("unchecked")
	public ArrayList<Words> sortStr()
	{
		TextToken.setStrSort();
		Collections.sort(wlist);
		return wlist;
	}
	
	/*sort word list by frequencies */
	@SuppressWarnings("unchecked")
	public ArrayList<Words> sortFreq()
	{
		TextToken.setFreqSort();
		Collections.sort(wlist);
		//System.out.println(wlist.toString());
		return wlist;
	}

	/*unique words found */
	public int uniqueWords()
	{
		return wlist.size();
	}
	
	/*Total words found */
	public int totalWords()
	{
		int count = 0;
		for(int i = 0; i < wlist.size();i++)
		{
			count += wlist.get(i).freq;
		}
		return count;
	}
	
	/*Number of sentences in text*/
	public int numSentences()
	{
		return sentenceCount;
	}
	
	/*Number of paragraphs in text */
	public int numParagraphs()
	{
		return paragraphCount;
	}
	
	/*prints frequencies of words that have frequencies of at least a target number */
	public void pFreqAtLeast(int target)
	{
		System.out.println("Finding all words with at least " + target + "occurances");
		for(int i = 0; i < wlist.size(); i++)
		{
			Words word = wlist.get(i);
			if(word.freq >= target)
			{
				System.out.print(word.str+"-"+word.freq);
				if(i < wlist.size()-2)
				{
					System.out.print(", ");
				}
			}
			
		}
		System.out.print('\n');
	}
	
	/*prints frequencies of words that have frequencies AT a target number */
	public void pFreqAt(int target)
	{
		System.out.println("Finding all words with " + target + "occurances");
		for(int i = 0; i < wlist.size(); i++)
		{
			Words word = wlist.get(i);
			if(word.freq == target)
			{
				System.out.print(word.str+"-"+word.freq);
				if(i < wlist.size()-2)
				{
					System.out.print(", ");
				}
			}
			
		}
		System.out.print('\n');
	}
	
	/*Looks for target number of words with highest counts */
	public void pMostCommon(int target)
	{
		sortFreq();
		System.out.println("Printing " + target + " most common words");
		for(int i = wlist.size()-1; i >= wlist.size()-target ;i--)
		{
			System.out.println(wlist.get(i).str + " - " + wlist.get(i).freq);
		}
	}
	
	/*searches a word */
	public boolean findWord(String target)
	{
		for(int i = 0; i < wlist.size();i++)
		{
			if(target.equalsIgnoreCase(wlist.get(i).str) )
			{
				return true;
			}
		}
		return false;
	}
	
	private boolean searchSentence(String tar, ArrayList<Words> sen)
	{
		for(int i = 0; i < sen.size();i++)
		{
			if(tar.equalsIgnoreCase(sen.get(i).str))
			{
				return true;
			}
		}
		return false;
	}
	public boolean findTwoWords(String tar1, String tar2)
	{
		for(int i = 0; i < slist.size();i++)
		{
			if(searchSentence(tar1, slist.get(i)) && searchSentence(tar2, slist.get(i)))
			{
				return true;
			}
		}
		return false;
	}
	
	public int findWords(String tar1, String tar2)
	{
		int count = 0;
		for(int i = 0; i < slist.size();i++)
		{
			if(searchSentence(tar1, slist.get(i)) && searchSentence(tar2, slist.get(i)))
			{
				count++;
			}
		}
		return count;
	}
}
