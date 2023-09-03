To run this site locally:

1. Install and setup Jekyll.
    1. For Windows, follow the instructions [here](https://jekyllrb.com/docs/installation/windows/) for installation and setup of Jekyll via RubyInstaller.
    1. For MacOS, follow the instructions [here](https://jekyllrb.com/docs/installation/macos/) for installation and setup of Jekyll via Homebrew.
2. cd into the root website folder
3. Run `bundle install` to install all required external packages for the website.
   1. If you see an error saying something about incompatible Ruby versions, delete the `Gemfile.lock` file and try running this command again.
4. Run `bundle exec jekyll serve` to build and serve the site. If there are no errors and you see **Server address: http://127.0.0.1:4000**, that means it worked. In a web browser (preferably Google Chrome), type **localhost:4000** in the address bar and hit enter. You should see the site.
