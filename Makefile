all:
	javac comp2150/reservatron/client/A3Client.java
	java comp2150/reservatron/client/A3Client
clean:
	rm -f comp2150/reservatron/client/*.class
	rm -f comp2150/reservatron/server/*.class
	rm -f comp2150/reservatron/server/session/*.class
	rm -f comp2150/reservatron/server/manager/*.class
	rm -f comp2150/reservatron/server/reserve/*.class
	rm -f comp2150/reservatron/server/hotel/*.class
	rm -f comp2150/reservatron/server/admin/*.class