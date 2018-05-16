package me.soulyana.bullhorn;

import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {

    Iterable<Post> findAllByAppUser(AppUser appUser);

    Iterable<Post> findAllByAppUserContaining(Iterable<AppUser> manyUsers);
}
