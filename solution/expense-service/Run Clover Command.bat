REM mvn clean clover2:setup test clover2:aggregate clover2:clover -Dsurefire.skip.tests=false;
mvn clean clover:setup test clover:aggregate clover:clover -Dsurefire.skip.tests=false;
REM start chrome.exe target\site\clover\index.html

