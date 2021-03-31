package cz.uhk.fim.dbs.musicDatabase.article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Article findFirstByOrderByIdDesc();

    List<Article> findFirst5ByOrderByDateDesc();

    List<Article> findAllByOrderByDateDesc();


}
