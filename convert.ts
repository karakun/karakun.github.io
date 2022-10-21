import { parse, stringify} from 'https://deno.land/std@0.160.0/encoding/yaml.ts'

const prefix = "---\n\
layout: talk\n\
";


function createFilename (title: string) {
  return title.replaceAll('&','+')
            .replaceAll(';', '')
            .replaceAll(':', '')
            .replaceAll('`', '')
            .replaceAll('’', '')
            .replaceAll('\'', '')
            .replaceAll(',', '')
            .replaceAll(' ', '-')
            .toLowerCase() + ".md"
} 

function convertContent(content: string){
    return  prefix 
            + content.replace('name:', 'title:')
                .replace('authors:', 'speakers:')
                .replace('author:', 'speakers:') 
            + "\n---\n\n" + talk.summary
}

const file = Deno.readTextFileSync('./_data/talks.yml', 'utf8')

const data = parse(file);

for (var talk of data) {
    talk.index = (43 - data.indexOf(talk));
    Deno.writeTextFileSync("./_talks/" + createFilename(talk.name), 
                           convertContent(stringify(talk)));
}

