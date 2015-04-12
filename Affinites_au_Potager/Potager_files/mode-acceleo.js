ace.define("ace/mode/acceleo_highlight_rules",["require","exports","module","ace/lib/oop","ace/mode/text_highlight_rules"], function(require, exports, module) {
"use strict";

var oop = require("../lib/oop");
var TextHighlightRules = require("./text_highlight_rules").TextHighlightRules;

var AcceleoHighlightRules = function() {

    var keywords = (
    "macro|query|extends|file|?|import|init|overrides|" +
                        "each|before|after|for|if|elseif|then|else|endif|let|elselet|trace|mode|text_explicit|" +
                        "code_explicit|super|stdout|protected|post"
    );

    var acceloOperation = (
	"(abs|addAll|ancestors|and|any|append|asBag|asOrderedSet|asSequence|asSet|at|collect|collectNested|concat|contains|count|current|" +
	    "div|drop|dropRight|eAllContents|eContainer|eContents|eGet|eInverse|endsWith|equalsIgnoreCase|excludes|excludesAll|excluding|exists|" +
	    "filter|first|flatten|floor|followingSiblings|forAll|getProperty|implies|includes|includesAll|including|index|indexOf|indexOfSlice|"+
	    "insertAt|intersection|invoke|isAlpha|isAlphanum|isEmpty|isUnique|last|lastIndex|lastIndexOf|lastIndexOfSlice|lineSeparator|matches|"+
	    "max|min|mod|not|notEmpty|oclAsType|oclIsInvalid|oclIsKindOf|oclIsTypeOf|oclIsUndefined|one|or|precedindSiblings|precedingSiblings|"+
	    "prefix|prepend|product|reject|removeAll|replace|replaceAll|reverse|round|select|sep|siblings|size|sortedBy|startsWith|strcmp|strstr|"+
	    "strtok|subOrderedSet|subSequence|substitute|substituteAll|substring|sum|symmetricDifference|toInteger|toLower|toLowerFirst|toReal|"+
	    "toString|toUpper|toUpperFirst|tokenize|tokenizeLine|trim|union|xor)(\\()"
    );
    
    var buildinConstants = ("null|true|false");

    var keywordMapper = this.createKeywordMapper({
        "variable.language": "self",
	"support.class": "module",
	"support.type": "template",
        "keyword": keywords,
	"storage.modifier":  "public|private",
        "constant.language": buildinConstants,
    }, "variable.other");


   this.$rules = {
        "start" : [
            {
                token:  "comment.block.documentation",
                regex:  "\\[\\*\\*",
                next:   "documentation"
            },
            {
                token:  "comment",
                regex:  "\\[comment\\]",
                next:   "commentMulti"
            },
	    {
                token:  "comment",
                regex:  "\\[comment\\s+",
                next:   "commentSingle"
            },{
                token:  "support.type",
                regex:  "\\[template|\\[module",
                next:   "templatestate"
            },{
                token:  "support.type",
                regex:  "\\[/template]"
            },
	    {
                token:  "keyword.statement",
                regex:  "\\[/",
                next:   "endBlock"
            },
            {
                token:  "keyword.statement",
                regex:  "\\[",
                next:   "statement"
            }
        ],
        "statement" : [
            {
                token:  "constant.numeric",
                regex:  "\\b[0-9]+\\.?[0-9]*\\b"
            },
            {
                token:  "string",
                regex:  /'[^'"]*'/
            },
            {
                token:  "string",
                regex:  /"[^"]*"/
            },
            {
                token:  "keyword.statement",
                regex:  "\\]",
                next:   "start"
            },
            {
                token:  "keyword.statement",
                regex:  "\\/]",
                next:   "start"
            },
	    {
              token : "punctuation.operator",
              regex : "->"
            }, 
            {
                 token:  "keyword.operator",
                regex:  "[-\\+<>\\|@=/]"
            },
            {
                token:  ["support.function", "paren.lparen"],
                regex:  acceloOperation
            },
            {
                token:  ["keyword", "text", "paren.lparen"],
                regex:  "(if|for|protected|file|elseif)(\\s*)(\\()"
            },
            {
                token:  ["entity.name.function", "text", "paren.lparen"],
                regex:  "([a-zA-Z_$][a-zA-Z0-9_$]*\\b)(\\s*)(\\()"
            },
	    {
                token:  "storage.modifier",
                regex:  "and|or|xor|imples|not"
            },
            {
                token:  keywordMapper,
                regex:  "[a-zA-Z_$][a-zA-Z0-9_$]*\\b"
            },
	    {
              token : "punctuation.operator",
              regex : "\\?|\\:|\\,|\\;|\\."
            }, 
	    {
                token : "paren.lparen",
                regex : "[[({]"
            },
	    {
                token : "paren.rparen",
                regex : "[\\])}]"
            },
	    {
                token : "text",
                regex : "\\s+"
            }
        ],"templatestate" : [
            {
                token:  "constant.numeric",
                regex:  "\\b[0-9]+\\.?[0-9]*\\b"
            },
            {
                token:  "string",
                regex:  /'[^'"]*'/
            },
            {
                token:  "string",
                regex:  /"[^"]*"/
            },
            {
                token:  "support.type",
                regex:  "\\]",
                next:   "start"
            },
            {
                token:  "support.type",
                regex:  "\\/]",
                next:   "start"
            },
	    {
              token : "punctuation.operator",
              regex : "->"
            }, 
            {
                 token:  "keyword.operator",
                regex:  "[-\\+<>\\|@=/]"
            },
            {
                token:  ["support.function", "paren.lparen"],
                regex:  acceloOperation
            },
            {
                token:  ["keyword", "text", "paren.lparen"],
                regex:  "(if|for|protected|file|elseif)(\\s*)(\\()"
            },
            {
                token:  ["entity.name.function", "text", "paren.lparen"],
                regex:  "([a-zA-Z_$][a-zA-Z0-9_$]*\\b)(\\s*)(\\()"
            },
	    {
                token:  "storage.modifier",
                regex:  "and|or|xor|imples|not"
            },
            {
                token:  keywordMapper,
                regex:  "[a-zA-Z_$][a-zA-Z0-9_$]*\\b"
            },
	    {
              token : "punctuation.operator",
              regex : "\\?|\\:|\\,|\\;|\\."
            }, 
	    {
                token : "paren.lparen",
                regex : "[[({]"
            },
	    {
                token : "paren.rparen",
                regex : "[\\])}]"
            },
	    {
                token : "text",
                regex : "\\s+"
            }
        ],
        "endBlock" : [
            {
                token:  "support.type",
                regex:  "template",
                next:   "statement"
            },
            {
                token:  "keyword.statement",
                regex:  "\\w+",
                next:   "statement"
            }
        ],
       "commentSingle" : [
            {
                token:  "comment",
                regex:  "/\\]",
                next:   "start"
            },
            {
                token:  "comment",
                regex:  "."
            }
        ],
        "commentMulti" : [
            {
                token:  "comment",
                regex:  "\\[/comment\\]",
                next:   "start"
            },
            {
                token:  "comment",
                regex:  "."
            }
        ],
        "documentation" : [
            {
                token:  "comment.block.documentation",
                regex:  "/\\]",
                next:   "start"
            },
            {
                token:  "comment.block.documentation",
                regex:  "."
            }
        ]
    };
};

oop.inherits(AcceleoHighlightRules, TextHighlightRules);

exports.AcceleoHighlightRules = AcceleoHighlightRules;

});


ace.define("ace/mode/folding/acceleostyle",["require","exports","module","ace/lib/oop","ace/range","ace/mode/folding/fold_mode"], function(require, exports, module) {
"use strict";

var oop = require("../../lib/oop");
var Range = require("../../range").Range;
var BaseFoldMode = require("./fold_mode").FoldMode;

var FoldMode = exports.FoldMode = function() {};
oop.inherits(FoldMode, BaseFoldMode);

(function() {

    this.foldingStartMarker = /\[template.*/;
    this.foldingStopMarker = /\[\/template]/;

    this.getFoldWidgetRange = function(session, foldStyle, row) {
    	var line = session.getLine(row);
    	var match = line.match(this.foldingStartMarker);
    	
    	if (match)
    	{
    		var endRow = row + 1;
    		var maxRow = session.getLength();
    		while (++endRow < maxRow)
    		{
    			line = session.getLine(endRow);
    			if (line.match(this.foldingStopMarker))
    			{
    				return new Range(row + 1, 0, endRow, line.length);
    			}
    		}
    	}
    	else if (line.match(this.foldingStopMarker))
    	{
    	    var endRow = row;
    	    while (--endRow > 0)
    	    {
    	    	line = session.getLine(endRow);
        		if (line.match(this.foldingStartMarker))
        		{
        		    return new Range(endRow + 1, 0, row, line.length);
        		}
    	    }
    	}
        		
        };

}).call(FoldMode.prototype);

});

ace.define("ace/mode/acceleo",["require","exports","module","ace/lib/oop","ace/mode/text","ace/tokenizer","ace/mode/acceleo_highlight_rules"], function(require, exports, module) {
"use strict";

var oop = require("../lib/oop");
var TextMode = require("./text").Mode;
var Tokenizer = require("../tokenizer").Tokenizer;
var AcceleoHighlightRules = require("./acceleo_highlight_rules").AcceleoHighlightRules;
var AcceleoFoldMode = require("./folding/acceleostyle").FoldMode;

var Mode = function() {
    this.HighlightRules = AcceleoHighlightRules;
    this.foldingRules = new AcceleoFoldMode();
};
oop.inherits(Mode, TextMode);

(function() {
    this.lineCommentStart = "[comment ";
    this.blockComment = {start: "[comment]", end: "[/comment]"};
    
    this.$id = "ace/mode/acceleo"
    
}).call(Mode.prototype);

exports.Mode = Mode;
});
