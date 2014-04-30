# TalismaneClient

TalismaneClient is a simple client for [Talismane](https://github.com/urieli/talismane) (in a server configuration). It's useful to treat large corpora "on-the-fly" in an [OOP](http://en.wikipedia.org/wiki/Object-oriented_programming) perspective.


## Install

1. Clone Talismane Project
        
        mkdir talismane
        cd talismane
        git clone https://github.com/urieli/talismane.git
    
2. Build Talismane wit Ant

        ant dist


3. Clone TalismaneClient in a new directory

        mkdir talismane_client
        cd talismane_client
        git clone https://github.com/jfaucon/talismane_client.git
        

## Usage

1. Run Talismane server
        
        cd talismane/dist/
        # For english
        java -Xmx2G -jar talismane-en-1.x.x-allDeps command=analyse endModule=parser mode=server encoding=UTF-8
        # For french
        java -Xmx2G -jar talismane-fr-1.x.x-allDeps command=analyse endModule=parser mode=server encoding=UTF-8
              
It's possible to have a verbose output with a different log4j configuration. See [example of log4j](http://github.com/jfaucon/talismane_client/blob/master/log4j.properties). Also, Talismane offers a lot of arguments. See [users's manual](http://urieli.github.io/talismane/)

        java -Xmx2G -jar talismane-en-1.x.x-allDeps command=analyse ... logConfigFile=log4j.properties
        

2. 
        
        
## Integration with Unix tools

1. Run Talismane server
2. Test

        echo "Les poules du couvent couvent." | java -jar talismaneClient.jar serveur port | cut -f2,4



        echo "Les poules du couvent couvent." | java -Xmx1024M -jar talismane-fr-version-allDeps.jar command=analyse | perl -pe 'END{print "tokens:$i\nsentences:$p\n"} if (/^\r$/) {$p++} else {$i++}'
