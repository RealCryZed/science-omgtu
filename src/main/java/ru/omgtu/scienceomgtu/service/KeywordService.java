package ru.omgtu.scienceomgtu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.omgtu.scienceomgtu.model.Keyword;
import ru.omgtu.scienceomgtu.model.KeywordsPublication;
import ru.omgtu.scienceomgtu.model.Publication;
import ru.omgtu.scienceomgtu.repository.KeywordsPublicationRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class KeywordService {

    @Autowired
    private KeywordsPublicationRepository keywordsPublicationRepository;

    public String getKeywordsByPublication(Publication publication) {
        List<KeywordsPublication> keywordsPublications = keywordsPublicationRepository.findKeywordsPublicationsByPublication(publication);
        List<Keyword> keywordsList = new ArrayList<>();

        for (KeywordsPublication keywordsPublication : keywordsPublications) {
            keywordsList.add(keywordsPublication.getKeyword());
        }

        StringBuilder keywords = new StringBuilder();

        for (int i = 0; i < keywordsList.size(); i++) {
            if (i == keywordsList.size() - 1) {
                keywords.append(keywordsList.get(i).getKeyword());
            } else {
                keywords.append(keywordsList.get(i).getKeyword()).append(", ");
            }
        }

        return keywords.toString();
    }
}
