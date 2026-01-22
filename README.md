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
