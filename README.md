# karakun.github.io
The developer community page of Karakun AG

## Build instructions

The most easy way to build the page locally is by using `docker-compose`.
We provide a `docker-compose.yml` file that starts a server that runs the page an forwards it to `localhost:4040`.
If you have installed [docker compose](https://docs.docker.com/compose/) on your machine you only need to call the following command from the root directory of the repository to get everything up and running:

```
docker-compose up
```

### Run jekyll on your machine

In order to start `start.sh` on your machine you have to install [_jekyll_](https://jekyllrb.com) (see instructions online).
If you want to install [_jekyll_](https://jekyllrb.com) ruby is needed on your machine.

If you have macOS and `brew` is installed you can easily execute the following commands:

```
brew install ruby
gem install bundler jekyll
```

Then you can use `start.sh` to start a server for local inspection (preview) of your markdown.

## Drafts

(adopted from [jekyll docs](https://jekyllrb.com/docs/posts/))

Drafts are posts without a date in the filename. They’re posts you’re still working on and don’t want to publish yet. To get up and running with drafts, create a _drafts folder in your site’s root and create your first draft:
```
|-- _drafts/
|   |-- a-draft-post.md
```
To preview your site with drafts, run jekyll serve or jekyll build with the --drafts switch. Each will be assigned the value modification time of the draft file for its date, and thus you will see currently edited drafts as the latest posts.

## Future posts

Future posts are posts on which supposed to be published in the future. Posts with date greater than the current date are not published by Jekyll. Future posts are created in the _posts directory, the only difference between a normal post and a future post is the date. To build the website with future posts simply run jekyll serve or jekyll build with --future.
