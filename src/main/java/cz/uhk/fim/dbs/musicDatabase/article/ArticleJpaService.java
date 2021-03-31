package cz.uhk.fim.dbs.musicDatabase.article;

import cz.uhk.fim.dbs.musicDatabase.db.BaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleJpaService implements BaseEntityService<Article> {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public Article findById(long id) {
        return articleRepository.findById(id).get();
    }

    @Override
    public Article create(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public Article update(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public void deleteById(long id) {
        articleRepository.deleteById(id);
    }

    public Article findTopByOrderByIdDesc() {
        return articleRepository.findFirstByOrderByIdDesc();
    }

    public List<Article> findFirst5ByOrderByDateDesc() {
        return articleRepository.findFirst5ByOrderByDateDesc();
    }

    public List<Article> findAllByOrderByDateDesc() {
        return articleRepository.findAllByOrderByDateDesc();
    }
}
