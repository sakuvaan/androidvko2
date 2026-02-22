## hox vko1 puuttuu, tein sen suoraan vko2 koska en kerenny palauttaa.


## Viikko 2

## Datamalli ja funktiot

Sovelluksessa on Task-data class, jossa on id, title, description, priority, dueDate ja done.  
Mock-dataa käytetään listan testaamiseen.

Tehtävien käsittelyyn on tehty omat funktiot:
- addTask lisää uuden tehtävän listaan
- toggleDone vaihtaa tehtävän done-tilan
- filterByDone suodattaa tehtävät
- sortByDueDate järjestää tehtävät päivämäärän mukaan

## Compose-tilanhallinta

Composessa UI päivittyy automaattisesti, kun tilan arvo muuttuu.  
Kun ViewModelissa oleva lista muuttuu, näkymä päivittyy ilman erillistä päivityskoodia.

## Miksi ViewModel eikä remember

ViewModelia käytetään, koska se pitää sovelluksen tilan erillään käyttöliittymästä.  
Se on parempi ratkaisu kuin pelkkä remember, kun sovellukseen tulee enemmän toiminnallisuutta.

## Viikko 3

### MVVM
Sovellus on tehty MVVM-mallilla.
Model sisältää datan (Task), ViewModel hoitaa tilan ja logiikan ja View (Compose) näyttää UI:n.
UI ei muokkaa dataa suoraan, vaan kutsuu ViewModelin funktioita.

### StateFlow Composessa
TaskViewModel käyttää StateFlowta listan tilaan.
HomeScreen kuuntelee sitä collectAsState():lla.
Kun lista muuttuu (add, toggle, remove, update), Compose päivittää näkymän automaattisesti.


# Task-app (Week 4)

Tämä projekti on jatkoa aiemmille viikoille. Sovellus on tehty Jetpack Composella ja noudattaa MVVM-arkkitehtuuria.  
Week 4:ssa mukaan lisättiin navigointi ja kalenterinäkymä.

## Rakenne (MVVM)
- **Model**
  - `Task` – tehtävän data (id, title, description, dueDate, done)
- **ViewModel**
  - `TaskViewModel`
  - Säilyttää tehtävälistan `StateFlow<List<Task>>`-muodossa
  - Funktiot: `addTask`, `toggleDone`, `removeTask`, `updateTask`
- **View (Compose)**
  - `HomeScreen` – tehtävälista + add/edit
  - `CalendarScreen` – tehtävät ryhmiteltynä päivämäärän mukaan
  - `DetailScreen` – tehtävän muokkaus / poisto dialogina
  - `AddTaskDialog` – uuden tehtävän lisääminen

## Navigointi (Compose Navigation)
Sovellus käyttää Jetpack Compose Navigationia.
- `NavHost` on MainActivityssa
- Reitit:
  - `home` → HomeScreen
  - `calendar` → CalendarScreen
- Sama `TaskViewModel` jaetaan molemmille ruuduille, joten tila pysyy synkassa.

## Tilan hallinta
- UI kuuntelee ViewModelia `collectAsState()`-kutsulla
- Kun tehtävää lisätään, muokataan tai poistetaan:
  - muutos näkyy heti sekä Home- että Calendar-näkymässä

## Kalenterinäkymä
- Tehtävät ryhmitellään `dueDate`-kentän perusteella
- Päivämäärä toimii otsikkona
- Tehtävää voi klikata ja avata editointi-dialogin

## Dialogit
- Lisääminen ja editointi tehdään `AlertDialog`illa
- Ei omia navigaatioreittejä dialogeille
- Sama dialogilogiikka toimii molemmissa näkymissä

# Week5 – Weather App

## Retrofit

Retrofit hoitaa HTTP-pyyntöjen tekemisen OpenWeatherMap API:in.
Se määrittelee rajapinnan (WeatherApi) ja tekee verkko-kutsun annetulla kaupungilla.

## JSON → dataluokat

API palauttaa JSON-vastauksen.
Gson muuntaa JSONin automaattisesti Kotlinin data class -rakenteeksi (WeatherResponse).

## Coroutines

API-kutsu tehdään coroutinella taustasäikeessä ViewModelissa.
UI ei jäädy, koska verkkokutsu ei blokkaa pääsäiettä.
Kun data saapuu, tila päivittyy.

## UI-tila

ViewModel hallitsee WeatherUiState-oliota (StateFlow).
Compose kuuntelee tilaa collectAsState()-funktiolla.
Kun tila muuttuu, UI päivittyy automaattisesti.

## API-key

API-key ei ole kovakoodattu.

Se tallennetaan:
local.properties → BuildConfig → Retrofit

local.properties:
OPENWEATHER_API_KEY=oma_avain

build.gradle.kts lukee avaimen ja lisää sen BuildConfigiin.
Retrofit käyttää BuildConfig.OPENWEATHER_API_KEY arvoa.

# Week6 – Weather app + Room

Tällä viikolla lisäsin sovellukseen Room-tietokannan.

Sovellus hakee säätiedot OpenWeatherMap API:sta ja tallentaa viimeisimmän haun Roomiin.
UI näyttää aina Roomista luetun datan.

## Mitä Room tekee?

Room on Androidin tietokantakirjasto.

Se helpottaa paikallisen tietokannan käyttöä, koska:
- määritellään Entity (taulu)
- tehdään DAO (kyselyt)
- luodaan Database
- käytetään Repositorya
- ViewModel käyttää Repositorya

Room generoi tarvittavan koodin taustalla.

## Miten data kulkee

1. Käyttäjä syöttää kaupungin
2. ViewModel kutsuu Repositorya
3. Repository hakee sään API:sta
4. Data tallennetaan Roomiin
5. UI kuuntelee Roomin Flow-dataa
6. Kun tietokanta päivittyy → UI päivittyy automaattisesti

## Välimuisti

Viimeisin haettu sää tallennetaan Roomiin.
UI näyttää aina Roomista luetun datan.

Halutessa voidaan tarkistaa, onko data liian vanhaa ja hakea API:sta uudelleen.

## Coroutines

- API-kutsu tehdään taustalla (viewModelScope.launch)
- DAO-funktiot ovat suspend
- UI ei jäädy haun aikana

## UI-tila

ViewModel hallitsee WeatherUiState-oliota.

Compose käyttää collectAsState()-funktiota,
joten kun tila muuttuu, UI päivittyy automaattisesti.

## API-key

API-avain on tallennettu näin:

local.properties → build.gradle → BuildConfig → Retrofit

API-key ei mene GitHubiin.