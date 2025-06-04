# Manager

Manager è un'applicazione Android pensata per organizzare partite di calcio amatoriali.
Permette di salvare i giocatori in un database locale, scegliere i partecipanti a una
partita e generare automaticamente due squadre bilanciate. L'app supporta sia la modalità a
5 che quella a 7 giocatori. Agitando il dispositivo è possibile rimescolare casualmente i
componenti delle squadre.

Questo progetto è stato realizzato come lavoro di esame di maturità durante il mio percorso
nella scuola superiore.

## Funzionalità principali
- Inserimento, modifica ed eliminazione dei giocatori mediante interfaccia dedicata
  e memorizzazione tramite SQLite.
- Selezione dei giocatori disponibili e creazione di squadre per partite a 5 o a 7.
- Possibilità di rimescolare le squadre tramite un pulsante o scuotendo il telefono
  grazie all'uso dell'accelerometro.

## Requisiti di compilazione
- Android SDK (minSdkVersion 16).
- Gradle (il progetto include gli script necessari).

Per generare l'applicazione è sufficiente eseguire
```
./gradlew assembleDebug
```
Il file APK risultante si troverà sotto `app/build/outputs/apk/`.

