/** This is a template for a Viterbi class, which can be used to
 * compute most likely sequences.  Fill in code for the constructor
 * and <tt>mostLikelySequence</tt> method.
 */
public class Viterbi {

	/**
	 * Private properties
	 */
	private Hmm m_hmm;
	private double m_bestPathValue[];
	private int m_parentState[][];
	private int m_numStates;
	
    /** This is the constructor for this class, which takes as input a
     * given HMM with respect to which most likely sequences will be
     * computed.
     */
    public Viterbi(Hmm hmm) 
    {
    	this.m_hmm = hmm;
    	this.m_numStates = this.m_hmm.getNumStates();
    }

    /** Returns the most likely state sequence for the given
     * <tt>output</tt> sequence, i.e., the state sequence of highest
     * conditional probability given the output sequence, according to
     * the HMM that was provided to the constructor.  The returned
     * state sequence should have the same number of elements as the
     * given output sequence.
     */
    public int[] mostLikelySequence(int output[]) 
    {
    	int[] mostLikelySequence = new int[output.length];
    	this.m_bestPathValue = new double[this.m_numStates - 1];
    	this.m_parentState = new int[this.m_numStates - 1][output.length];
    	
    	for(int time = 1; time < output.length; time++)
    	{
    		this.updateBestPathValue(output, time);
    	}
    	
    	double maxValue = Double.NEGATIVE_INFINITY;
    	int currentValueIndex = 0;
    	for(int currentState = 0; currentState < this.m_hmm.getNumStates() - 1; currentState++)
    	{
    		double currentValue = this.m_bestPathValue[currentState];
    		if(currentValue > maxValue)
    		{
    			currentValueIndex = currentState;
    			maxValue = currentValue;
    		}
    	}
    	
    	int optimalStateIndex = currentValueIndex;
    	for(int currentTimeIndex = output.length; currentTimeIndex > 0; currentTimeIndex--)
    	{
    		mostLikelySequence[currentTimeIndex - 1] = optimalStateIndex;
    		optimalStateIndex = this.m_parentState[optimalStateIndex][currentTimeIndex - 1];
    	}
    	
    	return mostLikelySequence;
    }
    
    /**Updates the bestPathValue variable and set m_parentState matrix
     * Source code taken from: https://github.com/ravifreek63/Artificial-Intelligence-Projects/blob/master/Viterbi/Viterbi.java
     * Author: Ravi Tandon
     * Modified by: Matjaz Suber
     * Date: 18.08.2014
     */
	private void updateBestPathValue(int[] output, int time) 
	{
		int currentOutput = output[time - 1];
		int parrentIndex;
		double[] tempPathValues = new double[this.m_numStates - 1];
		
		if(time == 1)
		{
			for(int currentState = 0; currentState < this.m_numStates - 1; currentState++)
			{
				this.m_bestPathValue[currentState] = (this.m_hmm.getLogStartProb(currentState)) + 
						(this.m_hmm.getLogOutputProb(currentState, currentOutput));
			}
		}
		else
		{
			for(int currentState = 0; currentState < this.m_numStates - 1; currentState++)
			{
				double maxValue = Double.NEGATIVE_INFINITY;
				double pathValue;
				parrentIndex = 0;
				
				for(int prevState = 0; prevState < this.m_numStates - 1; prevState++)
				{
					pathValue = this.m_bestPathValue[prevState] 
							+ (this.m_hmm.getLogTransProb(prevState, currentState)) 
							+ (this.m_hmm.getLogOutputProb(currentState, currentOutput));
					
					if(pathValue > maxValue)
					{
						maxValue = pathValue;
						parrentIndex = prevState;
					}
				}
				
				tempPathValues[currentState] = maxValue;
				this.m_parentState[currentState][time - 1] = parrentIndex;
			}
			
			for(int currentState = 0; currentState < this.m_numStates - 1; currentState++)
			{
				this.m_bestPathValue[currentState] = tempPathValues[currentState];
			}
		}
	}

}
