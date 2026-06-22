# Documento di Visione (Vision Document)
**Progetto:** Portale Universitario di Gestione Iscrizione Corsi
**Tecnologia:** 100% Java (Swing + JDBC)
**Sviluppatore:** [Il Tuo Nome]

## 1. Introduzione
Il presente documento definisce la visione e i requisiti ad alto livello per il sistema di gestione delle iscrizioni ai corsi universitari. L'obiettivo è risolvere le inefficienze nell'approvazione delle richieste di iscrizione degli studenti da parte dei docenti attraverso un'applicazione desktop snella, centralizzata e sicura.

## 2. Posizionamento del Prodotto
* **Per:** Studenti universitari e Professori.
* **Chi:** Necessitano di un canale diretto e formale per gestire l'accesso ai corsi d'esame.
* **Il Prodotto:** È un'applicazione desktop 100% Java basata sull'architettura DAO e interfacce Swing.
* **Che cosa fa:** Permette agli studenti di inviare richieste di iscrizione in un click e ai professori di approvarle in tempo reale attraverso una dashboard dedicata.

## 3. Descrizione dei Profili Utente (Attori)
* **Studente:** Utente che naviga l'offerta formativa, invia richieste di iscrizione e monitora lo stato della propria ammissione.
* **Professore:** Utente con privilegi di gestione. Visualizza esclusivamente le richieste pendenti relative ai propri corsi e ha il potere di approvarle.

## 4. Funzionalità Chiave del Prodotto
* **Autenticazione Unificata:** Un unico punto di accesso (`LoginFrame`) che smista dinamicamente l'utente alla propria dashboard in base al ruolo letto sul database (`Studente` o `Professore`).
* **Visualizzazione Condizionata:** I docenti vedono solo ed esclusivamente i dati di loro competenza grazie a query SQL ottimizzate per il loro identificativo.
* **Persistenza e Coerenza:** Aggiornamento dello stato delle richieste (`In Attesa` / `Approvato`) sincrono tra interfaccia grafica e database relazionale.
