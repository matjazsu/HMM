Struktura direktorija:

--------------------------------------------------------------

V imeniku HMM_matjaz_suber se nahajajo naslednji pod-imeniki, datoteke:

	- Data (vsebuje nabor podatkov za testiranje);
	- Izvorna koda (vsebuje implementacijo/java razrede programa RunViterbi);
	- Test (vsebuje dnevniške zapise testiranja delovanja aplikacije RunViterbi);


Navodila za prevajanje in poganjanje JAVA programa RunViterbi:

--------------------------------------------------------------

1. Prevajanje:

- s pomočjo ukazne vrstice se premaknemo v direktorij, ker se nahaja izvorna koda programa,

- s pomočjo java prevajalnika "javac" prevedemo program:

	javac RunViterbi.java


2. Poganjanje:

- preveden program poženemo na naslednji način:

	java RunViterbi {absolutna pot do *.data datoteke}

- primer poganjanja programa:

	java RunViterbi /Data/topics.data



O aplikaciji 'RunViterbi':
--------------------------------------------------------------

1. Organiziranost JAVA razredov:

- RunViterbi.java
	
	+ metoda main (zažene program; vsebuje inicializacijo HMM in Viterbi objekta; izpiše rezultate na STDOUT);
	
	+ inicializacija in uporaba objekta HMM:
		Hmm h = new Hmm(ds.numStates, ds.numOutputs, ds.trainState, ds.trainOutput);

	+ inicializacija in uporaba objekta Viterbi:
		Viterbi v = new Viterbi(h);
		int[] state = v.mostLikelySequence(ds.testOutput[i]);


- HMM.java

	+ metoda buildHMM (inicializira in nastavi vrednost matriki skritih stanj in matriki opazovanih stanj);


- Viterbi.java

	+ metoda mostLikelySequence (izračuna in vrne najbolj verjetno zaporedje skritih stanj za dana opazovana stanja);








