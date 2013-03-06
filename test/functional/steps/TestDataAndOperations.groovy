package steps

import rgms.publication.Periodico
import rgms.publication.PeriodicoController
import rgms.publication.ResearchLine
import rgms.publication.ResearchLineController
import rgms.member.Record
import rgms.member.RecordController

class TestDataAndOperations {
    static articles = [
            [journal: "Theoretical Computer Science", volume: 455, number: 1, pages: "2-30",
                    title: "A theory of software product line refinement",
                    publicationDate: (new Date("12 October 2012"))],
            [journal: "Science of Computer Programming", volume: 455, pages: "2-30",
                    title: "Algebraic reasoning for object-oriented programming",
                    publicationDate: (new Date("12 October 2012"))]
    ]
	
	static public def insertsResearchLines()
	{
		ResearchLine rl = new ResearchLine()
		rl.setName("Empirical Software Engineering")
		rl.setDescription("We are investigating processes, methods, techniques and tools for supporting empirical studies in software engineering. The main objective is to develop a infrastructure that support researchers to define, plan, execute, analyze and store results of empirical studies in general. At this moment we call such structure Testbed")
		rl.save()
		
		rl = new ResearchLine()
		rl.setName("Novo Padrao Arquitetural MVCE")
		rl.setDescription("Nova arquitetura que promete revolucionar a web")
		rl.save()
	}
	
	static researchLines = [ 
		[name: "IA Avancada", description: ""]
	]
	
	static records = [
		[status_H: "MSc Student",start: (new Date()), end: null]
	]

    static public def findArticleByTitle(String title) {
        articles.find { article ->
            article.title == title
        }
    }
	
	static public def findRecordByStatus(def status)
	{
		records.find{ record ->
			record.status_H == status			
		}
	}
	
	static public def findResearchLineByName(String name) {
		researchLines.find { researchLine ->
			researchLine.name == name
		}
	}

    static public boolean compatibleTo(article, title) {
        def testarticle = findArticleByTitle(title)
        def compatible = false
        if (testarticle == null && article == null) {
            compatible = true
        } else if (testarticle != null && article != null) {
            compatible = true
            testarticle.each { key, data ->
                compatible = compatible && (article."$key" == data)
            }
        }
        return compatible
    }

    static public void createArticle(String title, filename) {
        def cont = new PeriodicoController()
        def date = new Date()
        cont.params << TestDataAndOperations.findArticleByTitle(title) << [file: filename]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static void clearArticles() {
        Periodico.findAll()*.delete flush: true // Could also delete the created files.
    }
	
	static public void deleteResearchLine(def id){
		def res = new ResearchLineController()
		res.params.id = id
		res.request.setContent(new byte[1000]) // Could also vary the request content.
		res.delete()
		res.response.reset()
	}
	
	static public void updateResearchLine(String name,String description){
		def res = new ResearchLineController()
		def research_line = ResearchLine.findByName(name)		
		res.params.id = research_line.id
		res.params.name = research_line.name
		res.params.description = description
		res.request.setContent(new byte[1000]) // Could also vary the request content.
		res.update()
		res.response.reset()
	}
	
	static public void createResearchLine(String name) {
		def cont = new ResearchLineController()
		def research = TestDataAndOperations.findResearchLineByName(name)
		cont.params.name = research.name
		cont.params.description = research.description
		cont.request.setContent(new byte[1000]) // Could also vary the request content.
		cont.create()
		cont.save()
		cont.response.reset()
	}
	
	static public void deleteRecord(def id){
		def rec = new RecordController()
		rec.params.id = id
		rec.request.setContent(new byte[1000]) // Could also vary the request content.
		rec.delete()
		rec.response.reset()
	}
	static public void updateRecord(def status, String end){
		def record = Record.findByStatus_H(status)
		def rec = new RecordController()
		rec.params.id = record.id
		rec.params.start = record.start
		rec.params.status_H = record.status_H
		rec.params.end = Date.parse("dd/mm/yyyy",end)
		rec.request.setContent(new byte[1000]) // Could also vary the request content.
		rec.update()
		rec.response.reset()
	}
	
	static public def createRecord(def status)
	{
		def cont = new RecordController()
		def record = TestDataAndOperations.findRecordByStatus(status)
		cont.params.status_H = record.status_H
		cont.params.start = record.start
		cont.params.end = record.end
		cont.create()
		cont.save()
		cont.response.reset()
		
	}
	

}