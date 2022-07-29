ant compile package
cd test
ant compile package
ant -f build.xml runTest -Dtest=$1
cd ..
