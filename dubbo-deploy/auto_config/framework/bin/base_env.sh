# java home dir
JAVA_HOME=${javahome}
PRODUCTION=${productionModel}

UNIX_USER=${userName}

#DATABASE_OPTS=" -Ddatabase.codeset=ISO-8859-1 -Ddatabase.logging=false "
#URI_ENCODING=" -Dorg.eclipse.jetty.util.URI.charset=UTF-8 "

# JVM 
if [ "$PRODUCTION" = "product" ]; then
	JAVA_MEM_OPTS=" -DappName=${appName} -server -Xmx1g -Xms1g -Xmn256m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m -Xss512k -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=7 -XX:GCTimeRatio=19  -Xnoclassgc -XX:+UseParNewGC -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled -XX:+CMSParallelRemarkEnabled -XX:+CMSClassUnloadingEnabled -XX:-CMSParallelRemarkEnabled -XX:CMSInitiatingOccupancyFraction=70 -XX:SoftRefLRUPolicyMSPerMB=0 -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:+UseCompressedOops -XX:-HeapDumpOnOutOfMemoryError -XX:+PerfBypassFileSystemCheck"
else
	JAVA_MEM_OPTS=" -DappName=${appName} -server -Xmx512m -Xms512m -Xmn256m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m -Xss512k -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=7 -XX:GCTimeRatio=19 -Xnoclassgc -XX:+UseParNewGC -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled -XX:+CMSParallelRemarkEnabled -XX:+CMSClassUnloadingEnabled -XX:-CMSParallelRemarkEnabled -XX:CMSInitiatingOccupancyFraction=70 -XX:SoftRefLRUPolicyMSPerMB=0 -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:+UseCompressedOops -XX:-HeapDumpOnOutOfMemoryError -XX:+PerfBypassFileSystemCheck"
fi

# ?
JAVA_OPTS_EXT=" -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true -Dapplication.codeset=UTF-8 -Dfile.encoding=UTF-8 "

# remote DeBug
JAVA_DEBUG_OPT=" "
if [ "$PRODUCTION" = "product" ]; then
	production_mode=true;
else
	production_mode=false;
	JAVA_DEBUG_OPT=" -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=${appDebugPort},server=y,suspend=n "
fi

#elif [ "$PRODUCTION" = "test" ]; then
#  production_mode=false;

JAVA_OPTS=" $JAVA_MEM_OPTS $DATABASE_OPTS $JAVA_OPTS_EXT $URI_ENCODING $JAVA_DEBUG_OPT "
export JAVA_OPTS

cygwin=false;
case "`uname`" in
    CYGWIN*)
        cygwin=true
        ;;
esac

export DEPLOY_HOME JAVA_OPTS JAVA_HOME HTTPD_HOME JETTY_HOME JETTY_SERVER_HOME OUTPUT_HOME APP_NAME UNIX_USER production_mode cygwin
