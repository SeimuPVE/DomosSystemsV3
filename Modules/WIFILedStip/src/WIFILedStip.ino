#pragma clang diagnostic push
#pragma ide diagnostic ignored "CannotResolve"

#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <WiFiServer.h>
#include <ESP8266mDNS.h>

#include <WiFiUdp.h>
#include <ArduinoOTA.h>

#define L_PIN D0

const char* ssid = "zorhopewifi";
const char* password = "LeZorZorus!";

WiFiServer socketServer(8266);
WiFiClient socket;

IPAddress ip(192, 168, 0, 144);
IPAddress gateway(192, 168, 0, 254);
IPAddress subnet(255, 255, 255, 0);
char msg = '.';


void TCPServer();

void setup() {
	Serial.begin(115200);

    WiFi.config(ip, gateway, subnet);
    WiFi.begin(ssid, password);
    Serial.println("");

    while (WiFi.status() != WL_CONNECTED) {
        delay(500);
        Serial.print(".");
    }

    ArduinoOTA.setHostname("...");
    ArduinoOTA.setPassword("uefhp879");
    ArduinoOTA.begin();

    socketServer.begin();

    Serial.println("");
    Serial.print("IP address: ");
    Serial.println(WiFi.localIP());

	pinMode(16, OUTPUT);
	Serial.println("Module up !");
}

void loop() {
	if(WiFi.status() != WL_CONNECTED)
        WiFi.begin(ssid, password);

    if(WiFi.status() != WL_CONNECTED) // If still unconnected after reconnection try.
        delay(500);

    TCPServer();
	ArduinoOTA.handle();
}

void TCPServer() {
    socket = socketServer.available();

    if(socket && socket.connected())
        while(msg != 'E') {
            msg = (char) socket.read();

            if(msg == 'O') {
				digitalWrite(16, HIGH);
				socket.println("ON");
            }
            else if(msg == 'F') {
				digitalWrite(16, LOW);
				socket.println("OFF");
			}
        }

    msg = '.';
}

#pragma clang diagnostic pop
