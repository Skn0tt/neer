# Neer

## Was ist Neer?

Es ist Samstag Abend, ich gehe zum Auftakt der JazzTube-Konzertreihe am alten Zoll.
Als ich ankomme, schaue ich mich um, ob hier noch andere sind, die ich kenne.
Es ist aber zu unübersichtlich, ich sehe niemanden.
Irgendwann entdecke ich einen Bekannten von mir - als zu ihm gehe, um "Hallo" zu sagen, ist er auf einmal verschwunden.

Mit Neer würde so ablaufen:
Sobald ich beim Konzert ankomme, schalte ich Neer an und wähle alle Freund\*innen aus, die ich heute Abend zu treffen bereit bin.
Damit das nicht so viel Klick-Arbeit ist, kann ich Gruppen erstellen, die ich dann mit einem Klick auswählen kann.
Meine Freund\*innen machen heute Abend das selbe.

Sobald sich von den ausgewählten Freund*innen jemand in meiner Nähe befindet, werden wir beide benachrichtigt.
Den Suchradius können wir dabei frei einstellen (100m, 500m, 1km, 2km, 5km, 10km).
Wir werden nur benachrichtigt, wenn wir uns im eingestellten Radius des/der jeweils Andere\*n befinden und wir beide bereit sind, den/die jeweils Andere\*n zu treffen.
Ist dies nicht der Fall, bekommen wir beide nichts davon mit - ein wenig wie bei Tinder.
Sobald wir uns wieder von einander weg bewegen, einer von uns Neer abschaltet oder den anderen heute Abend nicht mehr treffen möchte, wird die Benachrichtigung übrigens zurückgezogen.

Nun können wir uns beide überlegen, ob wir uns wirklich treffen wollen.
Neer bietet dafür einen internen Chat an, kann aber auch einfach WhatsApp oder einen anderen Messenger öffnen.
Damit ist Neers Arbeit getan - falls jemand in der Nähe ist, den ich kenne, kann ich ihn jetzt treffen.
Problem gelöst.

## Ähnliche Dienste

Seit ein paar Monaten gibt es das Facebook-Feature "Nearby Friends".
Es hat einen sehr ähnlichen Nutzen, jedoch kann man dort den Umkreis nicht selbst einstellen.
Außerdem ist es von Facebook, das schließt es für viele Menschen aus - sowohl, weil Facebook out ist, als auch, weil viele ältere Facebook den Rücken kehren.

Daneben gibt es ["Sup"](https://suptheapp.com).
Das scheint aber tot zu sein:
Es gibt nur eine iOS-App, die Android-Version ist für 2017 angekündigt (steht jetzt noch auf der Website), aber ist nicht erschienen.

## Weitere Überlegungen

Es wäre super, wenn man sich in Neer (ähnlich wie bei WhatsApp) über seine Telefonnummer registrieren könnte.
Damit wäre ein großes Problem gelöst: Man müsste nicht mehr umständlich jeden Freund in sein "Neer-Kontaktbuch" eintragen.
Für die Nutzer, die Neer ihre Telefonnummer nicht geben möchten, würde ich aber trotzdem eine anonyme Identifizierungs-Methode anbieten.
Dafür würde ich zufällige, kurze Strings verteilen, die man als QR-Code (ähnlich wie die Freundescodes bei Pokémon Go) teilen kann.
Wichtig: Nutzer, die sich über Telefonnummer authentifizieren, müsste zusätzlich einen solchen QR-Code haben - denn nicht jeder möchte seine Telefonnummer weitergeben.

### Technische Umsetzung

Technisch sollte es keine allzu komplizierte App werden.
Die App, die auf Android und iOS verfügbar ist, sendet (während der Nutzer sie aktiviert hat) jede Positionsänderung an den Server.
Dieser rechnet dann die Distanz zu allen Freunden, die den Nutzer treffen will (und die den Nutzer treffen wollen), aus und vergleicht die Radien.
Die Laufzeit ist damit linear.

Kompliziert könnte werden, mit SMS und Push Notifications zu arbeiten - das sollte aber gut machbar sein.
