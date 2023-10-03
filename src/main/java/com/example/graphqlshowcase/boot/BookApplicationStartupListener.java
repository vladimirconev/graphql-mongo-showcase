package com.example.graphqlshowcase.boot;

import com.example.graphqlshowcase.domain.BookRepository;
import com.example.graphqlshowcase.domain.valueobject.Address;
import com.example.graphqlshowcase.domain.valueobject.Author;
import com.example.graphqlshowcase.domain.valueobject.Genre;
import com.example.graphqlshowcase.domain.valueobject.ISBN;
import com.example.graphqlshowcase.domain.valueobject.Publisher;
import java.util.List;
import java.util.Random;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Startup listener to load simple dummy data in Mongo DB if 'initialDataLoad' is set to True.
 *
 * @author Vladimir.Conev
 */
public class BookApplicationStartupListener implements ApplicationListener<ContextRefreshedEvent> {

  private final Boolean initialDataLoad;
  private final BookRepository bookRepository;

  public BookApplicationStartupListener(
      final Boolean initialDataLoad, final BookRepository bookRepository) {
    this.initialDataLoad = initialDataLoad;
    this.bookRepository = bookRepository;
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    if (initialDataLoad) {
      bookRepository.deleteAllBooks();

      var pearsonHeadquatersAddress =
          new Address("23A", "United Kingdom", "London", "EC1A", "88 Park Road");
      var relxHeadquatersAddress =
          new Address("76/2A", "United Kingdom", "London", "EC5A", "34 London St Road");
      var thomsonReutersHeadquatersAddress =
          new Address("2A", "Canada", "Toronto", "66777", "Main Road str");
      var penguinRandomHouseAddress = new Address("456", "USA", "NYC", "10001", "5th Avenue");
      var hacheteLivreAddress = new Address("254", "France", "Paris", "75000", "Rue de V.");

      var pearson = new Publisher("PEARSON", pearsonHeadquatersAddress);
      var relx = new Publisher("RELX", relxHeadquatersAddress);
      var thomsonReuters = new Publisher("Thomson Reuters", thomsonReutersHeadquatersAddress);
      var penguin = new Publisher("PENGUIN", penguinRandomHouseAddress);
      var hacheteLivre = new Publisher("HACHETE LIVRE", hacheteLivreAddress);

      var elaineBloyd = new Author("Elaine", "Bloyd", "elaine.bloyd@pearson.com");
      var mitchTimotchy = new Author("Mitch", "Timotchy", "mt@relx.com");
      var foo = new Author("Foo", "Boo", "foo_boo@mail.com");
      var boo = new Author("Boo", "Foo", "boo_foo@mail.com");
      var antonioVilla = new Author("Antonio", "Villa", "antonio_villa@hachetelivre.fr");

      var isbnList =
          List.of(
              new ISBN("978-1-891830-25-9"),
              new ISBN("978-1-60309-047-6"),
              new ISBN("978-1-891830-85-3"),
              new ISBN("978-1-60309-478-8"),
              new ISBN("978-1-891830-85-3"),
              new ISBN("978-1-60309-369-9"));

      Random random = new Random();

      bookRepository.createBook(
          isbnList.get(random.nextInt(isbnList.size())),
          Genre.FANTASY,
          "Cloud Hotel",
          List.of(foo, boo),
          pearson);
      bookRepository.createBook(
          isbnList.get(random.nextInt(isbnList.size())),
          Genre.CONTEMPORARY,
          "Dragon Puncher Book1",
          List.of(elaineBloyd),
          relx);
      bookRepository.createBook(
          isbnList.get(random.nextInt(isbnList.size())),
          Genre.SCI_FI,
          "Johny Boo",
          List.of(mitchTimotchy),
          penguin);
      bookRepository.createBook(
          isbnList.get(random.nextInt(isbnList.size())),
          Genre.MYSTERY,
          "River of Ghosts",
          List.of(antonioVilla),
          hacheteLivre);
      bookRepository.createBook(
          isbnList.get(random.nextInt(isbnList.size())),
          Genre.ROMANCE,
          "Our Expanding Universe",
          List.of(foo),
          thomsonReuters);

      bookRepository.createBook(
          isbnList.get(random.nextInt(isbnList.size())),
          Genre.THRILLER,
          "Ninja Chicken",
          List.of(foo, boo, mitchTimotchy),
          penguin);

      bookRepository.createBook(
          isbnList.get(random.nextInt(isbnList.size())),
          Genre.DYSTOPIAN,
          "A clockwork Orange",
          List.of(foo, boo),
          pearson);
    }
  }
}
