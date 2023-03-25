function Vertex(word) {
    this.word = word;
    this.adjacent = new Set();
}

function WordGraph(validWords, ops) {
    this.validWords = validWords;
    this.ops = ops;
    this.map = new Map();
};

function WordTrace(graph, startWord, endWord, ops) {
    this.graph = graph;
    this.startWord = startWord;
    this.endWord = endWord;
    this.ops = ops;
    this.parents = new Map();
}

async function loadWords(filename) {
    console.log("Loading words...");
    const res = await fetch(filename);
    const text = await res.text();
    return new Set(text.split("\n"));
}

function getAdjacent(word, g) {
    const alphabet = "abcdefghijklmnopqrstuvwxyz";
    const adjacent = new Set();

    for (let ichar = 0; ichar < word.length; ichar++) {
        if (g.ops[2]) {
            const rmAdjacent = word.slice(0, ichar) + word.slice(ichar + 1);
            adjacent.add(rmAdjacent);
        }
        if (g.ops[0]) {
            for (const letter of alphabet) {
                const setAdjacent = word.slice(0, ichar) + letter + word.slice(ichar + 1);
                adjacent.add(setAdjacent);
            }
        }
    }
    if (g.ops[1]) {
        for (let ipos = 0; ipos < word.length + 1; ipos++) {
            for (const letter of alphabet) {
                const addAdjacent = word.slice(0, ipos) + letter + word.slice(ipos);
                adjacent.add(addAdjacent);
            }
        }
    }

    const validAdjacent = new Set();
    for (const adj of adjacent) {
        if (g.validWords.has(adj)) {
            validAdjacent.add(g.map.get(adj));
        }
    }
    validAdjacent.delete(g.map.get(word));
    return validAdjacent;
}

function BFS(t) {
    if (!t.graph.validWords.has(t.startWord)) {
        return 2;
    }
    if (!t.graph.validWords.has(t.endWord)) {
        return 3;
    }

    const queue = [];
    const visited = new Set();

    const start = t.graph.map.get(t.startWord);
    queue.push(start);
    visited.add(start);

    if (t.startWord == t.endWord) {
        return 0;
    }

    while (true) {
        const current = queue.shift();
        if (current == undefined) {
            return 1;
        }

        for (const adj of current.adjacent) {
            if (visited.has(adj)) {
                continue;
            }

            if (adj.word == t.endWord) {
                t.parents.set(adj, current);
                return 0;
            }

            t.parents.set(adj, current);
            queue.push(adj);
            visited.add(adj);
        }
    }

}

async function main() {
    const filename = "wordsets/words-58k.txt";
    const validWords = await loadWords(filename);

    let g = new WordGraph(validWords, null);

    const submit = document.getElementById("submit");
    submit.addEventListener("click", function() {
        g = onClick(g);
    }
        );
}

function logResult(result, t) {
    const output = document.getElementById("output");
    switch(result) {
        case 0:
            output.innerText += " \u{2705}";
            output.style.color = "green";
            break;
        case 1:
            output.innerText += " \u{274C}";
            output.style.color = "red";
            break;
        case 2:
            output.innerText += " \u{26A0}"
            output.style.color = "orange";
    }

    if (t == null) {
        return;
    }

    const trace = [];
    let current = t.graph.map.get(t.endWord);
    while (current != null) {
        trace.push(current.word);
        current = t.parents.get(current);
    }
    const distance = trace.length - 1;

    let display = "";
    for (let i = 0; i < distance; i++) {
        display += trace.pop() + " -->\n";
    }
    display += `${trace.pop()} (${distance})`;

    output.innerText += "\n" + display;
}

function onClick(g) {
    const startWord = document.getElementById("start").value.toLowerCase();
    const endWord = document.getElementById("end").value.toLowerCase();

    const output = document.getElementById("output");
    output.innerText = `${startWord} -> ${endWord}`;

    const newops = [false, false, false];
    if (document.getElementById("set").checked) {
        newops[0] = true;
    }
    if (document.getElementById("add").checked) {
        newops[1] = true;
    }
    if (document.getElementById("remove").checked) {
        newops[2] = true;
    }

    let needsRefresh = false;
    if (g.ops == null || g.ops.toString() != newops.toString()) {
        console.log("refresh");
        needsRefresh = true;
        g.ops = newops;
    }

    if ((!g.ops[0] && !g.ops[1] && !g.ops[2]) ||
        (!g.ops[1] && !g.ops[2]
        && startWord.length != endWord.length) ||
        (g.ops[0] && g.ops[1] && !g.ops[2]
        && startWord.length > endWord.length) ||
        (!g.ops[0] && !g.ops[1] && g.ops[2]
        && startWord.length < endWord.length) ||
        (!g.ops[0] && (!g.ops[1] || !g.ops[2])
        && startWord.length == endWord.length)) {
            // console.log("Short circuit:");
            // console.log(`startWord ${startWord} has length ${startWord.length}`);
            // console.log(`endWord ${endWord} has length ${endWord.length}`);
            // console.log(`Set valid: ${g.ops[0]}`)
            // console.log(`Add valid: ${g.ops[1]}`)
            // console.log(`Remove valid: ${g.ops[2]}`)
            // console.log("\n");



            logResult(2, null);
            return g;
    }
    if (needsRefresh) {
        g = buildGraph(g.validWords, g.ops);
    }

    const t = new WordTrace(g, startWord, endWord, g.ops);

    logResult(BFS(t), t);
    return g;
    
}


function buildGraph(validWords, ops) {
    const g = new WordGraph(validWords, ops);



    console.log("Generating graph...");
    for (const word of validWords) {
        const v = new Vertex(word);
        g.map.set(word, v);
    }

    for (const v of g.map.values()) {
        v.adjacent = getAdjacent(v.word, g);
    }
    console.log("Graph generated");
    return g;

}

main();