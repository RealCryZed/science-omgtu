package ru.omgtu.scienceomgtu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.omgtu.scienceomgtu.model.Author;
import ru.omgtu.scienceomgtu.model.Publication;
import ru.omgtu.scienceomgtu.model.Source;
import ru.omgtu.scienceomgtu.service.AuthorService;
import ru.omgtu.scienceomgtu.service.FilterService;
import ru.omgtu.scienceomgtu.service.PublicationService;
import ru.omgtu.scienceomgtu.service.SourceService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController {
    private int page_size = 20;
    @Autowired
    private FilterService filterService;
    @Autowired
    private AuthorService authorService;

    @Autowired
    private SourceService sourceService;

    @Autowired
    private PublicationService publicationService;

    @GetMapping ("/admin")
    public ModelAndView getAdminPage(ModelAndView modelAndView) {
        modelAndView.setViewName("admin");
        return modelAndView;
    }

    @GetMapping ("/admin/add-author")
    public ModelAndView getAddProfilePage(ModelAndView modelAndView) {
        modelAndView.addObject("author", new Author());
        modelAndView.setViewName("add-author");
        return modelAndView;
    }

    @PostMapping("/admin/add-author")
    public ModelAndView addAuthor(ModelAndView modelAndView,
                                  @Valid @ModelAttribute("author") Author author,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject(author);
            modelAndView.setViewName("/add-author");
        } else {
            authorService.addAuthor(author);
            modelAndView.addObject("successMessage", "Автор успешно добавлен!");
            modelAndView.addObject("author", new Author());
            modelAndView.setViewName("admin");
        }

        return modelAndView;
    }

    @GetMapping("/admin/edit-author/page/{offset}")
    public ModelAndView getAllAuthorsFiltered(ModelAndView modelAndView, @PathVariable int offset,
                                                   @RequestParam(required = false) Integer pageSize) {
        if (pageSize == null) pageSize = page_size;
        else page_size = pageSize;

        Page<Author> allAuthors = authorService.findAuthorsWithPagination(offset, pageSize);

        modelAndView.addObject("pageSize", page_size);
        modelAndView.addObject("currentPage", offset);
        modelAndView.addObject("totalPages", allAuthors.getTotalPages() - 1);
        filterFunction(modelAndView);

        modelAndView.addObject("authors", allAuthors.getContent());
        modelAndView.setViewName("edit-author");
        return modelAndView;
    }

    @GetMapping("/admin/edit-author/delete/{id}")
    public ModelAndView deleteAuthor(ModelAndView modelAndView, @PathVariable Integer id) {
        authorService.deleteAuthor(id);
        modelAndView.setViewName("admin");

        return modelAndView;
    }

    @GetMapping("/admin/edit-author/{id}")
    public ModelAndView getAuthorOnEdit(ModelAndView modelAndView, @PathVariable Integer id) {
        Author author = authorService.getAuthorById(id);
        modelAndView.addObject("author", author);
        modelAndView.setViewName("edit-single-author");

        return modelAndView;
    }

    @PostMapping("/admin/edit-author/{id}")
    public ModelAndView editAuthor(ModelAndView modelAndView,
                                  @PathVariable Integer id,
                                  @Valid @ModelAttribute("author") Author author,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject(author);
            modelAndView.setViewName("/edit-author/" + id);
        } else {
            authorService.addAuthor(author);
            modelAndView.addObject("successMessage", "Автор успешно отредактирован!");
            modelAndView.addObject("author", new Author());
            modelAndView.setViewName("redirect:/admin");
        }

        return modelAndView;
    }

    @GetMapping ("/admin/add-source")
    public ModelAndView getAddSourcePage(ModelAndView modelAndView) {
        modelAndView.addObject("sourceTypes", sourceService.getAllSourceTypes());
        modelAndView.addObject("source", new Source());
        modelAndView.setViewName("add-source");
        return modelAndView;
    }

    @PostMapping("/admin/add-source")
    public ModelAndView addSource(ModelAndView modelAndView,
                                  @Valid @ModelAttribute("source") Source source,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("sourceTypes", sourceService.getAllSourceTypes());
            modelAndView.addObject(source);
            modelAndView.setViewName("/add-source");
        } else {
            source.setSourceType(sourceService.findSourceTypeByName(source.getSourceTypeString()));
            sourceService.addSource(source);
            modelAndView.addObject("successMessage", "Источник успешно добавлен!");
            modelAndView.addObject("source", new Source());
            modelAndView.setViewName("admin");
        }

        return modelAndView;
    }

    @GetMapping ("/admin/edit-source/page/{offset}")
    public ModelAndView getEditAllSourcesPage(ModelAndView modelAndView, @PathVariable int offset,
                                              @RequestParam(required = false) Integer pageSize) {
        if (pageSize == null) pageSize = page_size;
        else page_size = pageSize;

        Page<Source> allSources = sourceService.findSourcesWithPagination(offset, pageSize);

        modelAndView.addObject("pageSize", page_size);
        modelAndView.addObject("currentPage", offset);
        modelAndView.addObject("totalPages", allSources.getTotalPages() - 1);
        filterFunction(modelAndView);

        modelAndView.addObject("sources", allSources.getContent());
        modelAndView.setViewName("edit-source");
        return modelAndView;
    }

    @GetMapping("/admin/edit-source/delete/{id}")
    public ModelAndView deleteSource(ModelAndView modelAndView, @PathVariable Integer id) {
        sourceService.deleteSource(id);
        modelAndView.setViewName("admin");

        return modelAndView;
    }

    @GetMapping ("/admin/edit-source/{id}")
    public ModelAndView getEditSourcePage(ModelAndView modelAndView,
                                          @PathVariable Integer id) {
        modelAndView.addObject("source", sourceService.findSourceById(id));
        modelAndView.addObject("sourceTypes", sourceService.getAllSourceTypes());
        modelAndView.setViewName("edit-single-source");
        return modelAndView;
    }

    @PostMapping("/admin/edit-source/{id}")
    public ModelAndView editSource(ModelAndView modelAndView,
                                   @PathVariable Integer id,
                                   @Valid @ModelAttribute("editSource") Source editSource,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Source source = sourceService.findSourceById(editSource.getId());
            modelAndView.addObject("source", source);
            modelAndView.setViewName("/admin/edit-source/" + id);
        } else {
            editSource.setSourceType(sourceService.findSourceTypeByName(editSource.getSourceTypeString()));
            sourceService.editSource(editSource);
            modelAndView.addObject("successMessage", "Источник успешно отредактирован!");
            modelAndView.addObject("source", new Source());
            modelAndView.setViewName("redirect:/admin");
        }

        return modelAndView;
    }

    @GetMapping ("/admin/add-publication")
    public ModelAndView getAddPublicationPage(ModelAndView modelAndView) {
        modelAndView.addObject("publicationTypes", publicationService.getPublicationTypes());
        modelAndView.addObject("sources", sourceService.getAllSources());
        modelAndView.addObject("authors", authorService.getAllAuthors());
        modelAndView.addObject("publication", new Publication());
        modelAndView.setViewName("add-publication");
        return modelAndView;
    }

    @PostMapping("/admin/add-publication")
    public ModelAndView addPublication(ModelAndView modelAndView,
                                  @Valid @ModelAttribute("publication") Publication publication,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("publicationTypes", publicationService.getPublicationTypes());
            modelAndView.addObject("sources", sourceService.getAllSources());
            modelAndView.addObject(publication);
            modelAndView.setViewName("/add-publication");
        } else {
            publication.setSource(sourceService.findSourceByName(publication.getSourceString()));
            publication.setType(publicationService.findPublicationTypeByName(publication.getPublicationType()));

            publicationService.addPublication(publication);

            modelAndView.addObject("successMessage", "Публикация успешно добавлена!");
            modelAndView.addObject("publication", new Publication());
            modelAndView.setViewName("admin");
        }

        return modelAndView;
    }

    @GetMapping("/admin/edit-publication")
    public ModelAndView getEditPublicationPage(ModelAndView modelAndView) {
        modelAndView.setViewName("edit-publication");
        return modelAndView;
    }

    @GetMapping ("/admin/edit-publication/page/{offset}")
    public ModelAndView getEditAllPublicationsPage(ModelAndView modelAndView, @PathVariable int offset,
                                              @RequestParam(required = false) Integer pageSize) {
        if (pageSize == null) pageSize = page_size;
        else page_size = pageSize;

        Page<Publication> allPublications = publicationService.findPublicationsWithPagination(offset, pageSize);

        modelAndView.addObject("pageSize", page_size);
        modelAndView.addObject("currentPage", offset);
        modelAndView.addObject("totalPages", allPublications.getTotalPages() - 1);
        filterFunction(modelAndView);

        modelAndView.addObject("publications", allPublications.getContent());
        modelAndView.setViewName("edit-publication");
        return modelAndView;
    }

    @GetMapping("/admin/edit-publication/delete/{id}")
    public ModelAndView deletePublication(ModelAndView modelAndView, @PathVariable Integer id) {
        publicationService.deletePublication(id);
        modelAndView.setViewName("admin");

        return modelAndView;
    }

    @GetMapping ("/admin/edit-publication/{id}")
    public ModelAndView getEditPublicationPage(ModelAndView modelAndView,
                                          @PathVariable Integer id) {
        Publication publication = publicationService.findPublicationById(id);

        LinkedList<String> authorsString = new LinkedList<>();

        for (Author author : authorService.getAuthorListByPublication(publication)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(author.getSurname()).append(" ").append(author.getName());
            if (author.getPatronymic() != null) {
                stringBuilder.append(" ").append(author.getPatronymic());
            }
            authorsString.add(stringBuilder.toString());
        }

        String authors = authorsString
                .stream()
                .map(name -> ("" + name + ""))
                .collect(Collectors.joining(","));
        publication.setAuthors(authors);

        modelAndView.addObject("publicationTypes", publicationService.getPublicationTypes());
        modelAndView.addObject("sources", sourceService.getAllSources());
        modelAndView.addObject("publication", publication);
        modelAndView.setViewName("edit-single-publication");
        return modelAndView;
    }

    @PostMapping("/admin/edit-publication/{id}")
    public ModelAndView editPublication(ModelAndView modelAndView,
                                   @PathVariable Integer id,
                                   @Valid @ModelAttribute("editPublication") Publication editPublication,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("publicationTypes", publicationService.getPublicationTypes());
            modelAndView.addObject("sources", sourceService.getAllSources());
            Publication publication = publicationService.findPublicationById(editPublication.getId());
            modelAndView.addObject("publication", publication);
            modelAndView.setViewName("/admin/edit-publication/" + id);
        } else {
            editPublication.setSource(sourceService.findSourceByName(editPublication.getSourceString()));
            editPublication.setType(publicationService.findPublicationTypeByName(editPublication.getPublicationType()));

            publicationService.addPublication(editPublication);
            modelAndView.addObject("successMessage", "Публикация успешно отредактирована!");
            modelAndView.addObject("publication", new Source());
            modelAndView.setViewName("redirect:/admin");
        }

        return modelAndView;
    }

    private void filterFunction(ModelAndView modelAndView) {
        modelAndView.addObject("ratings", filterService.getRatings());
        modelAndView.addObject("departments", filterService.getDepartments());
        modelAndView.addObject("publicationTypes", filterService.getPublicationTypes());
    }
}
