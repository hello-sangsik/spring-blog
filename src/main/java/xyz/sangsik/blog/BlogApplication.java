package xyz.sangsik.blog;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import xyz.sangsik.blog.domain.Category;
import xyz.sangsik.blog.entity.Post;
import xyz.sangsik.blog.entity.Role;
import xyz.sangsik.blog.entity.User;
import xyz.sangsik.blog.repository.PostRepository;
import xyz.sangsik.blog.repository.RoleRepository;
import xyz.sangsik.blog.repository.UserRepository;
import xyz.sangsik.blog.service.UserService;

@SpringBootApplication
public class BlogApplication {
    @Component
    class DummyData implements CommandLineRunner {


        private final PostRepository postRepository;
        private final UserRepository userRepository;
        private final RoleRepository roleRepository;
        private final UserService userService;

        @Autowired
        public DummyData(PostRepository postRepository, UserRepository userRepository, RoleRepository roleRepository, UserService userService) {
            this.postRepository = postRepository;
            this.userRepository = userRepository;
            this.roleRepository = roleRepository;
            this.userService = userService;
        }

        @Override
        public void run(String... args) throws Exception {

            Role r1 = roleRepository.save(new Role("USER"));

            User[] users = {
                    new User("sangsik", "qwe123")
                    , new User("sion", "qwe123")
                    , new User("iuu", "qwe123")
            };

            for (User u : users) {
                userService.add(u);
            }


            Category category = null;
            Lorem lorem = LoremIpsum.getInstance();

            for (int i = 1; i <= 500; i++) {

                switch (i % 3) {
                    case 0:
                        category = Category.IT;
                        break;
                    case 1:
                        category = Category.PROGRAMMING;
                        break;
                    case 2:
                        category = Category.TRAVEL;
                        break;
                }
                postRepository.save(new Post(category
                        , lorem.getTitle(1, 10)
                        , lorem.getParagraphs(1, 5)
                        , userRepository.findOne((long) (Math.random() * 3 + 1))));
            }
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }
}
