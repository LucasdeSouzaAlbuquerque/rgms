/**
 * Created with IntelliJ IDEA.
 * User: dyego
 * Date: 22/02/14
 * Time: 15:30
 * To change this template use File | Settings | File Templates.
 */

import pages.BookCreatePage
import pages.BookEditPage
import pages.BookPage
import pages.BookShowPage
import pages.LoginPage
import pages.PublicationsPage
import rgms.publication.Book
import rgms.tool.TwitterTool
import steps.BookTestDataAndOperations

import static cucumber.api.groovy.EN.*

Given(~'^the system has no book entitled "([^"]*)"$') { String title ->
    checkIfExists(title)
}

When(~'^I create the book "([^"]*)" with file name "([^"]*)"$') { String title, String filename ->
    BookTestDataAndOperations.createBook(title, filename)
}

Then(~'^the book "([^"]*)" is properly stored by the system$') { String title ->
    book = Book.findByTitle(title)
    assert BookTestDataAndOperations.bookCompatibleTo(book, title)
}

Given(~'^the book "([^"]*)" is stored in the system with file name "([^"]*)"$') { String title, String filename ->
    BookTestDataAndOperations.createBook(title, filename)
    book = Book.findByTitle(title)
    assert BookTestDataAndOperations.bookCompatibleTo(book, title)
}

When(~'^I remove the book "([^"]*)"$') { String title ->
    BookTestDataAndOperations.removeBook(title)
}

/*
 * @author droa
 * BEGIN
 */

When(~'^I select the new book option at the book page$') {->
    selectNewBookInBookPage()
}

def selectNewBookInBookPage(){
    at BookPage
    page.selectNewBook()
    at BookCreatePage
}

Then(~'^I can fill the book details$') {->
    at BookCreatePage
    page.fillBookDetails()
    page.selectCreateBook()
}

When(~'I go to the page of the "([^"]*)" book$') { String title ->
    at BookPage
    page.selectBook(title)
}

And(~'I follow the delete button confirming with OK$') { ->
    at BookShowPage
    page.select('input', 'delete')
}

And(~'^I click on Share to share the book on Twitter with "([^"]*)" and "([^"]*)"$') { String twitterLogin, String twitterPw ->
    at BookShowPage
    page.clickOnTwitteIt(twitterLogin, twitterPw)
}

Then(~'^a pop-up window with a tweet regarding the new book "([^"]*)" is shown$') { String bookTitle ->
    TwitterTool.addTwitterHistory(bookTitle, "added")
    assert TwitterTool.consult(bookTitle)
}

Given(~'^the book "([^"]*)" is in the book list with file name "([^"]*)"$') { String title, String filename ->
    book = Book.findByTitle(title)
    if (book == null) {
        page.selectNewBook()
        at BookCreatePage
        page.fillBookDetails(BookTestDataAndOperations.path() + filename, title)
        page.selectCreateBook()
    }

    to BookPage
}

When(~'^I select to edit the book "([^"]*)" in resulting list$') { String title ->
    at BookPage
    page.selectBook(title)

    at BookShowPage
    page.select('a', 'edit')

    at BookEditPage
}

Then(~'^I can change the book details$') { ->
    def path = new File(".").getCanonicalPath() + File.separator + "test" + File.separator + "files" + File.separator
    page.edit("Next Generation Software Product Line Engineering REVIEWED", path + "NGS2.pdf")

    page.doEdit()
}

/* END */

Then(~'^the book "([^"]*)" is properly removed by the system$') { String title ->
    checkIfExists(title)
}

Then(~'^the book "([^"]*)" is not stored twice$') { String title ->
    books = Book.findAllByTitle(title)
    assert books.size() == 1
}

When(~'^I edit the book title from "([^"]*)" to "([^"]*)"$') { String oldtitle, newtitle ->
    def updatedBook = BookTestDataAndOperations.editBook(oldtitle, newtitle)
    assert updatedBook != null
}

Then(~'^the book "([^"]*)" is properly updated by the system$') { String title ->
    assert Book.findByTitle(title) != null
}

Given(~'^the system has no books stored$') { ->
    initialSize = Book.findAll().size()
    assert initialSize == 0
}

When(~'^I upload the books of "([^"]*)"$') { filename ->
    initialSize = Book.findAll().size()
    BookTestDataAndOperations.uploadBook(filename)
    finalSize = Book.findAll().size()
    assert initialSize < finalSize
}

Then(~'^the system has all the books of the xml file$') { ->
    assert Book.findByTitle("Proceedings of the IV Brazilian Symposium on Programming Languages") != null
    assert Book.findByTitle("Testing Techniques in Software Engineering") != null
    assert Book.findByTitle("Proceedings of the XXIII Brazilian Symposium on Software Engineering") != null
    assert Book.findByTitle("Anais da IV ConferÃªncia Latina-Americana em Linguagens de PadrÃµes para ProgramaÃ§Ã£o (SugarLoafPLoP 2004)") != null
    assert Book.findByTitle("AOSD 2011 Proceedings and Companion Material") != null
}

And(~'^I am at the Book Page$') { ->
    at PublicationsPage
    to BookPage
}

When(~'^I go to new book page$') { ->
    to BookPage
    page.selectNewBook()
}

And(~'^I try to create a book named "([^"]*)" with filename "([^"]*)"$') { String title, String filename ->
    selectNewBookInBookPage()
    page.fillBookDetails(BookTestDataAndOperations.path() + filename, title)
    page.selectCreateBook()
}

Then(~'^the book "([^"]*)" was stored by the system$') { String title ->
    book = Book.findByTitle(title)
    assert book != null
    to BookPage
    at BookPage
}

When(~'^I select the download button$') { ->
    at BookPage
    page.downloadFile()
}

Then(~'^the download the file named "([^"]*) is properly filed"$') { String filename ->
    at BookPage
    assert page.downloadFile(book, title)
}

def checkIfExists(String title) {
    book = Book.findByTitle(title)
    assert book == null
}

def downloadFile(String filename){
    return
}