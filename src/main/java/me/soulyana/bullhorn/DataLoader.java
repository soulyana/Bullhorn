package me.soulyana.bullhorn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    AppUserRepository users;

    @Autowired
    PostRepository posts;

    @Autowired
    CommentRepository comments;

    @Override
    public void run(String...strings)throws Exception {

        Comment comment1 = new Comment("I like Chris Browns new song! check it out","5/14/2017");
        comments.save(comment1);

        Comment comment2 = new Comment("I like JCole new song! check it out","5/17/2018");
        comments.save(comment2);


        Post post1 = new Post("New Chris Brown Song","5/07/2017");
        posts.save(post1);

        Post post2 = new Post("New JCole Song","5/08/2017");
        posts.save(post2);

        AppUser appUser = new AppUser("jack","password","Jack", "https://inspired.disney.co.uk/wp-content/uploads/2017/04/disneyinspired-potc-quiz-v02-660x660-1.jpg");
        users.save(appUser);


        AppUser appUser1 = new AppUser("nicki","password", " Nicki", "https://i.ytimg.com/vi/4JipHEz53sU/maxresdefault.jpg");
        users.save(appUser1);
//        post1.addUser(appUser1);
//        comment1.addUser(appUser1);
        posts.save(post1);
        comments.save(comment1);

        AppUser appUser2 = new AppUser("dave","password","dave","https://www.esquireme.com/sites/default/files/images/2018/01/29/dave-franco-getty-images-mono.jpg");
        users.save(appUser2);

        AppUser appUser3 = new AppUser("50cent","password","Curtis","http://data.junkee.com/wp-content/uploads/2017/07/50-cent-2.png");
        users.save(appUser3);










    }
}