package ru.omgtu.scienceomgtu.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ru.omgtu.scienceomgtu.model.Publication;
import ru.omgtu.scienceomgtu.service.FilterService;
import ru.omgtu.scienceomgtu.service.PublicationService;

import java.util.List;

@Controller
public class PublicationController {

    @Autowired
    private PublicationService publicationService;

    @Autowired
    private FilterService filterService;

    @GetMapping("/publications/page/{offset}")
    public ModelAndView getAllPublicationsFiltered(ModelAndView modelAndView, @PathVariable int offset,
                                                   @Param("pageSize") int pageSize) {
        Page<Publication> allPublications = publicationService.findPublicationsWithPagination(offset, pageSize);

        modelAndView.addObject("currentPage", offset);
        modelAndView.addObject("totalPages", allPublications.getTotalPages() - 1);

        modelAndView.addObject("ratings", filterService.getRatings());
        modelAndView.addObject("departments", filterService.getDepartments());
        modelAndView.addObject("publicationTypes", filterService.getPublicationTypes());

        modelAndView.addObject("publications", allPublications.getContent());
        modelAndView.setViewName("publications");
        return modelAndView;
    }

    @GetMapping("/publications")
    public ModelAndView getAllPublications(ModelAndView modelAndView) {

        modelAndView.addObject("ratings", filterService.getRatings());
        modelAndView.addObject("departments", filterService.getDepartments());
        modelAndView.addObject("publicationTypes", filterService.getPublicationTypes());

        modelAndView.addObject("publications", publicationService.getTop20Publications());
        modelAndView.setViewName("publications");
        return modelAndView;
    }

    @GetMapping("/publication/{id}")
    public ModelAndView getPublication(ModelAndView modelAndView, @PathVariable Integer id) {
        Publication publication = publicationService.getPublicationById(id);
        modelAndView.addObject("publication", publication);
        modelAndView.setViewName("publication");

        return modelAndView;
    }
}
