# TalismaneClient

TalismaneClient is a simple client for [Talismane](https://github.com/urieli/talismane) server. Talismane is a framework for statistical natural language processing. [TalismaneClient](https://github.com/jfaucon/talismane_client) allows to treat corpora "on-the-fly" in a Unix pipeline or in a Java environment.



## Install

1. Clone Talismane Project
        
        git clone https://github.com/urieli/talismane.git
        cd talismane
    
2. Build Talismane with Ant

        ant jar


3. Clone TalismaneClient in a new directory

        git clone https://github.com/jfaucon/talismane_client.git
        cd talismane_client
        
        
4. Build TalismaneClient with Ant

        ant jar
        

## Run server

* Run standard server :
    * for English :
```      
cd talismane/dist/
java -Xmx2G -jar talismane-en-1.x.x-allDeps.jar command=analyse endModule=parser mode=server encoding=UTF-8
```     
    * for French :
```
java -Xmx2G -jar talismane-fr-1.x.x-allDeps.jar command=analyse endModule=parser mode=server encoding=UTF-8
```
              
* It's possible to have a verbose output with a different log4j configuration. An example is proposed in _/talismane_client/external/_ directory. 
```
    cp /talismane_client/external/log4j.properties /talismane/dist/
    java -Xmx2G -jar talismane-fr-1.x.x-allDeps.jar  ... logConfigFile=log4j.properties
```
* By default, Talismane runs on port 7272 and can be reached on "localhost" (in a local use). Also, Talismane can be configured with others several parameters. See [Talismane users's manual](http://urieli.github.io/talismane/).



## Usage in Unix Tools

* Use __--help__ argument to show usage instructions. The * indicates default values for arguments.
    
       java -jar talismane_client_1.0.0b.jar --help

       Usage:
       java -jar talismane_client-1.x.x.jar host=[*host] port=[*7272] pathFileInput=[*stdin or path] 
       encoding=[*UTF-8] pathFileStdIn=[*false|true]




* TalismaneClient can read stdin and treat entirely a corpus as a stream. It's usefull in a Unix pipeline.

    * Process a string with multiple sentences.
```      
    echo "Jean a poussé Marie. Marie est tombée." | java -jar talismane-client-1.0.0b.jar
        -------1-------
        1       Jean    jean    NC      nc      g=m|n=s         3       suj     3       suj
        2       a       avoir   V       v       n=s|p=3|t=pst   3       aux_tps 3       aux_tps
        3       poussé  pousser VPP     v       g=m|n=s|t=past  0       root    0       root
        4       Marie   Marie   NPP     np      g=f|n=s         3       obj     3       obj
        5       .       .       PONCT   PONCT   _               3       ponct   3       ponct

        -------2-------
        1       Marie   marie   NC      nc      g=f|n=s         3       suj     3       suj
        2       est     être    V       v       n=s|p=3|t=pst   3       aux_tps 3       aux_tps
        3       tombée  tomber  VPP     v       g=f|n=s|t=past  0       root    0       root
        4       .       .       PONCT   PONCT   _               3       ponct   3       ponct
```
 
 * Remove sentence markers
 
   ```     
    echo "Jean a poussé Marie. Marie est tombée." | java -jar talismane-client-1.0.0b.jar
       1       Jean    jean    NC      nc      g=m|n=s         3       suj     3       suj
       2       a       avoir   V       v       n=s|p=3|t=pst   3       aux_tps 3       aux_tps
       3       poussé  pousser VPP     v       g=m|n=s|t=past  0       root    0       root
       4       Marie   Marie   NPP     np      g=f|n=s         3       obj     3       obj
       5       .       .       PONCT   PONCT   _               3       ponct   3       ponct

       1       Marie   marie   NC      nc      g=f|n=s         3       suj     3       suj
       2       est     être    V       v       n=s|p=3|t=pst   3       aux_tps 3       aux_tps
       3       tombée  tomber  VPP     v       g=f|n=s|t=past  0       root    0       root
       4       .       .       PONCT   PONCT   _               3       ponct   3       ponct
   ```  

  * Print only tokens and postags
  
    ```     
    echo "Mais Marie n'a pas dit son dernier mot." | java -jar talismane-client-1.0.0b.jar | cut -f2,4
      -------1-------
      Mais    CC
      Marie   NPP
      n'      ADV
      a       V
      pas     ADV
      dit     VPP
      son     DET
      dernier ADJ
      mot     NC
      .       PONCT

    ``` 

  * Process one file and redirect output.
```    
      cat samples/stendhal_sample_1.txt | java -jar talismane-client-1.0.0b.jar > out.txt
```
     
 * Process multiple files and treat them as only one.
          
         find samples/stendhal_sample_*.txt -exec cat '{}' \; | java -jar talismane-client-1.0.0b.jar 
               
  * Process multiple files and store them seperatly. Here, the argument _pathFileStdIn=true_ specifies that stdin contains for each line the path to a file of corpus. The tag @NEWFILE is used to mark the begin of a file in the output. The name of processed files are file_n.txt (e.g: file_1.txt, file_2.txt, etc.).
          find ./samples/stendhal_sample_*.txt | java -jar talismane-client-1.0.0b.jar pathFileStdIn=true | perl -pe '$f or open $f,"> file_".++$i.".txt" if $_ !~/\@NEWFILE/; if(/^\@NEWFILE/){undef $f}else{print $f $_}'

## Usage in Java environment


* Import talismane-client-1.0.0b.jar in your java project.

       cp talismane_client/dist/talismane-client-1.0.0b.jar /path/toyour/project
       chmod u+x /path/toyour/project/talismane-client-1.0.0b.jar
          
       export CLASSPATH="$CLASSPATH:/path/toyour/project/talismane-client-1.0.0b.jar"
          
    
* In your project, you can call talismane-client like this :
```java
	/* default values : localhost, port 7272 and UTF-8 */
	TalismaneClient talismane_client = new TalismaneClient();
    talismane_client.analyse("Marie a poussé Jean.");
```

* TalismaneClient proposes simple data models (CoNNL_Structure > CoNNL_Sentence > CoNNL_token) to deal with Talismane's output in a [OOP](http://en.wikipedia.org/wiki/Object-oriented_programming) perspective. These data models are based on Talismane default's output format, which is [CoNNL format](http://nextens.uvt.nl/depparse-wiki/DataFormat").
 * Process a file (batch mode)

    ```java
	String text = talismane_client.readFile("./samples/stendhal_sample_1.txt");
	CoNLL_Structure stendhal_conll = talismane_client.analyse(text);
```

  * Print first lemma of each sentence.

    ```java
    CoNLL_Structure stendhal_conll = talismane_client.analyse(text);
	for(CoNLL_Sentence currSentence : stendhal_conll){
		CoNLL_Token currToken = currSentence.get(0);
		System.out.println(currToken.getLemma());
	}
		
    ```
 * Process a text without printing in stdout and print the first sentence.

    ```java
  CoNLL_Structure struct_connll = talismane_client.analyse(text,false);
  CoNLL_Sentence sentence = struct_connll.get(0);
  sentence.print();

    ```






## Others
* TalismaneClient have the same licence as Talismane (AGPL 3.0).
* A [python wrapper](https://github.com/enavarro222/talismane_python) for Talismane exits.

