package src;

public class DecisionTree
{
	TreeNode head;
	TreeNode ptr;
	
	public DecisionTree()
	{
		head = new TreeNode();
		ptr = head;
	}
	
	public TreeNode movePtrDown(int edge)
	{
		for(int i = 0; i < ptr.children.size(); i++)
		{
			if(ptr.children.get(i).upwardEdge.id() == edge)
			{
				ptr = ptr.children.get(i);
			}
		}
		return ptr;
	}
	
	/*TreeNode findCategory(ArrayList)
	{
		if(!ptr.isLeaf)
		{
			
		}
		return ptr;
	}*/
	
	public TreeNode resetPtr()
	{
		ptr = head;
		return ptr;
	}
}
