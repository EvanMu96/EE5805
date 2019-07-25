javac MsgServer.java -Xlint
javac MsgClient.java

nohup java MsgServer &

for x in 1 2 3
do
	nohup java MsgClient &
done

