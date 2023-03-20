Ziel der Aufgabe ist es, ein Java-Programm zu erstellen, welches Folgendes leistet:

1. Man kann ein RSA-Schlusselpaar realistischer Grösse generieren lassen. Dazu ist im Ein-
zelnen zu tun:

(✅) Mit Hilfe der Klasse BigInteger sollen zwei unterschiedliche Primzahlen zufällig
generiert und multipliziert werden.
(✅) Es soll ein geeignetes e gewahlt werden und dazu das passende d bestimmt werden.
Dazu ist insbesondere der erweiterte euklidische Algorithmus zu implementieren.
(✅) Der private Schlussel soll in einer Datei sk.txt in der Form (n, d) mit n und d in
Dezimaldarstellung abgespeichert werden, der  ̈offentliche in einer Datei pk.txt in
der Form (n, e).

2. Man kann eine Textdatei (ASCII) text.txt in einen String einlesen lassen und diesen
String wie folgt verschlüsseln:

(✅) Man liest einen  ̈offentlichen Schlussel aus einer Datei  ̈pk.txt ein.
(✅) Jedes Zeichen von text.txt wird in seinen ASCII-Code umgewandelt (Zahl zwischen
0 und 127).
(✅) Jedes Zeichen wird gem ̈ass dem RSA-Verfahren verschlusselt. Dazu ist insbesondere  ̈
der Algorithmus der schnellen Exponentiation zu implementieren.

(✅) Die Verschlusselung erfolgt in eine Datei 'chiffre.txt', wobei die einzelnen Ver-
schlusselungen in Dezimaldarstellung mit Komma getrennt hintereinander geschrieben werden.
Bemerkung: Diese zeichenweise Verschlusselung ist unsicher, da statistische Analysen  ̈
m ̈oglich sind. (Die Buchstabenh ̈aufigkeiten des zu verschlusselnden Textes  ̈ ubertragen sich  ̈
auf den Chiffretext.) RSA wird deshalb in der Praxis auch nicht so verwendet. Die ge-
naue Verwendung von RSA wird im Modul “Kryptographie und Informationssicherheit” thematisiert und geht uber das in dieser Aufgabe Machbare hinaus.

3. Man kann eine Datei chiffre.txt, die wie im obigen Punkt zustande gekommen ist,
mit einem privaten Schlussel aus  ̈ sk.txt entschlusseln und den resultierenden Text in  ̈
text-d.txt ausgeben.
4. Entschlüsseln Sie die gegebene Datei  ̈ chiffre.txt mit dem gegebenen Schlussel aus  ̈
sk.txt.


Allgemeine Hinweise:
1. Sie können in Gruppen bis zu drei Personen arbeiten.
2. Bei vollst ̈andiger Lösung wird auf die Note des kommenden Tests 0.3 drauf addiert. (Aus
systemtechnischen Grunden liegt die Erfahrungsnote zwischen 1.0 und 6.0.)  ̈
3. Es ist nicht nötig, das Programm hinsichtlich Effizienz zu optimieren.
4. Das Programm sollte verst ̈andlich kommentiert sein.
5. Eigentlich gehe ich davon aus, dass Sie aus Fairnessgrunden nicht versuchen, zu betrügen. Dennoch werde ich dies (auch mit Hilfe von Tools) kontrollieren. Falls dabei ein Täuschungsversuch festgestellt wird (also: (verschleierte) Kopien von Teilen existierender
Programme (Internet oder Kollegen)), wird die Note des nächsten Tests auf 1.0 gesetzt.
Abgabe: 26.03.23, per Mail. Fügen Sie die Entschlüsselung der von mir gegebenen Datei bei!