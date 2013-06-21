package pages

import geb.Page

class ArticleCreatePage extends Page {
    static url = "periodico/create"

    static at = {
        title ==~ /Criar Periodico/
        journal != null
    }

    static content = {
        journal {
            $("input", id: "journal")
        }
    }

    def fillArticleDetails() {
        $("form").title = "A theory of Software Product Line Refinement"
        $("form").journal = "Theoretical Computer Science"
        $("form").file = "./test/functional/steps/TCS.pdf"
        $("form").volume = 455
        $("form").number = 1
        $("form").pages = "2-30"
        // Could parametrize, obtaining data from class TestDataAndOperations
    }

    def selectCreateArticle() {
        $("input", name: "create").click()
    }
}