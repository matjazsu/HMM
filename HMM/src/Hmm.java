/** This is a template for an HMM class.  Fill in code for the
 * constructor and all of the methods.  Do not change the signature of
 * any of these, and do not add any other public fields, methods or
 * constructors (but of course it is okay to add private stuff).  All
 * public access to this class must be via the constructor and methods
 * specified here.
 */
public class Hmm {

	/**
	 * Private properties
	 */
	private int m_numStates;
	private int m_numOutputs;
	private double m_transitionProbability[][];
	private double m_emisionProbability[][];
	private int m_dummyStateIndex;
	
    /** Constructs an HMM from the given data.  The HMM will have
     * <tt>numStates</tt> possible states and <tt>numOutputs</tt>
     * possible outputs.  The HMM is then built from the given set of
     * state and output sequences.  In particular,
     * <tt>state[i][j]</tt> is the <tt>j</tt>-th element of the
     * <tt>i</tt>-th state sequence, and similarly for
     * <tt>output[i][j]</tt>.
     */
    public Hmm(int numStates, int numOutputs, int state[][], int output[][]) 
    {
       	this.m_numStates = numStates + 1; //numStates + dummy state
    	this.m_numOutputs = numOutputs;
    	this.m_dummyStateIndex = numStates - 1; //index for the dummy state
    	
    	//Initialize transition probability table
    	this.m_transitionProbability = new double[this.m_numStates][this.m_numStates];
    	
    	//Initialize emision probability table
    	this.m_emisionProbability = new double[this.m_numStates][this.m_numOutputs];
    	
    	//Build probabilities
    	this.buildHMM(state, output);
    }

    /** Returns the number of states in this HMM. 
     *	Number of Hidden states
     */
    public int getNumStates() 
    {
    	return this.m_numStates;
    }

    /** Returns the number of output symbols for this HMM. 
     *	Number of Observable states
     */
    public int getNumOutputs() 
    {
    	return this.m_numOutputs;
    }

    /** Returns the log probability assigned by this HMM to a
     * transition from the dummy start state to the given
     * <tt>state</tt>.
     */
    public double getLogStartProb(int state) 
    {
    	return this.m_transitionProbability[this.m_dummyStateIndex][state];
    }

    /** Returns the log probability assigned by this HMM to a
     * transition from <tt>fromState</tt> to <tt>toState</tt>.
     */
    public double getLogTransProb(int fromState, int toState) 
    {
    	return this.m_transitionProbability[fromState][toState];
    }

    /** Returns the log probability of <tt>state</tt> emitting
     * <tt>output</tt>.
     */
    public double getLogOutputProb(int state, int output) 
    {
    	return this.m_emisionProbability[state][output];
    }
    
    /**Fill the matrix m_transitionProbability, m_emisionProbability with computed probabilities
     * Source code taken from: https://github.com/ravifreek63/Artificial-Intelligence-Projects/blob/master/Viterbi/Hmm.java
     * Author: Ravi Tandon
     * Modified by: Matjaz Suber
     * Date: 18.08.2014
     */
    private void buildHMM(int state[][], int output[][])
    {
    	int currentState;
    	
    	int[][] transitionCount = new int[this.m_numStates][this.m_numStates];
    	int[][] outputProbabilitiesCount = new int[this.m_numStates][this.m_numOutputs];
    	int[] outputCount = new int[this.m_numStates];
    	int[] transitionsState = new int[this.m_numStates];
    	
    	for(int row = 0; row < state.length; row++)
    	{
    		int lastState = this.m_dummyStateIndex;
    		for(int column = 0; column < state[row].length; column++)
    		{
    			currentState = state[row][column];
    			transitionCount[lastState][currentState] += 1;
    			outputProbabilitiesCount[currentState][output[row][column]] += 1;
    			transitionsState[lastState] += 1;
    			outputCount[currentState] += 1;
    			lastState = currentState;
    		}
    	}
    	
    	this.updateTransitionProbability(transitionCount, transitionsState);
    	this.updateEmisionProbabilit(outputProbabilitiesCount, outputCount);
    }
    
    private void updateTransitionProbability(int [][] transitionCount, int[] transitionState)
    {
    	for(int row = 0; row < this.m_numStates; row ++)
    	{
    		for(int column = 0; column < this.m_numStates - 1; column ++)
    		{
    			this.m_transitionProbability[row][column] = 
    					Math.log((((double)transitionCount[row][column] + 1) / (transitionState[row] + this.m_numStates - 1)));
    		}
    	}
    }

    private void updateEmisionProbabilit(int[][] outputProbabilitesCount, int[] outputCount)
    {
    	for(int row = 0; row < this.m_numStates - 1; row ++)
    	{
    		for(int column = 0; column < this.m_numOutputs; column ++)
    		{
    			this.m_emisionProbability[row][column] = 
    					Math.log((((double)outputProbabilitesCount[row][column] + 1) / (outputCount[row] + this.m_numOutputs)));
    		}
    	}
    }
}
