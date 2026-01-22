# Viikko 2 – Task-sovellus (Jetpack Compose)

## Yleiskuvaus
Tämä projekti on jatkoa Viikko 1 -tehtävälle. Sovellus on toteutettu Jetpack Compose -tekniikalla ja käyttää ViewModelia UI-tilan hallintaan. Sovellus näyttää tehtävälistan, jota voi muokata UI:n kautta.

---

## Rakenne
Projektin rakenne on jaettu selkeästi:

- `domain/`
  - `Task.kt` – data class tehtävälle
  - `MockData.kt` – alkuperäinen mock-data
  - `TaskFunctions.kt` – puhtaat Kotlin-funktiot
- `ui/`
  - `HomeScreen.kt` – Compose UI
  - `TaskViewModel.kt` – ViewModel, joka hallitsee listan tilaa
- `MainActivity.kt` – käynnistää sovelluksen ja näyttää HomeScreenin

Domain, UI ja ViewModel on eroteltu toisistaan.

---

## ViewModel
Sovelluksessa käytetään `TaskViewModel`-luokkaa, joka perii `ViewModel`-luokan.

ViewModel:
- säilyttää tehtävälistan tilan
- tarjoaa funktiot listan muokkaamiseen
- huolehtii siitä, että UI päivittyy automaattisesti tilan muuttuessa

Tila ei ole enää `remember`-muuttujassa, vaan ViewModelissa.

---

## Toteutetut toiminnot
ViewModelin kautta toteutetut toiminnot:
- tehtävän lisääminen (add)
- tehtävän done-tilan vaihtaminen (toggle)
- tehtävän poistaminen (remove)
- tehtävien suodatus (All / Done / Not done)
- tehtävien järjestäminen päivämäärän mukaan (sort)

---

## Käyttöliittymä (UI)
Käyttöliittymä on toteutettu Jetpack Composella ilman XML:ää.

UI sisältää:
- TextField-kentät tehtävän otsikolle ja kuvaukselle
- Add-painike uuden tehtävän lisäämiseen
- Sort-painike tehtävien järjestämiseen
- All / Done / Not done -painikkeet suodatukseen
- LazyColumn-listan tehtäville
- Jokaisella rivillä:
  - Checkbox (done-tila)
  - tehtävän tiedot
  - Delete-painike

Kun ViewModelin tila muuttuu, Compose päivittää näkymän automaattisesti.

---

## Miksi ViewModel?
ViewModel:
- erottaa UI:n ja logiikan toisistaan
- säilyttää tilan uudelleenpiirrosten yli
- tekee sovelluksesta selkeämmän ja laajennettavamman

ViewModel on parempi ratkaisu kuin pelkkä `remember`, kun sovelluksen toiminnallisuus kasvaa.
