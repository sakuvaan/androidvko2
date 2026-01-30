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
