javac \
-cp /Applications/Processing.app/Contents/Java/core/library/core.jar \
goita/*.java \
goita/server/*.java \
goita/server/state/*.java \
-d ../exe

javac \
-cp /Applications/Processing.app/Contents/Java/core/library/core.jar \
goita/*.java \
goita/client/*.java \
goita/client/state/*.java \
goita/client/viewer/*.java \
-d ../exe
