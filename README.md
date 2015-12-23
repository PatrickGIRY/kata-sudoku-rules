# Les règles du Sudoku

Les règles du Sudoku explique qu'une grille est valide si et seulement si on trouve 1 et une seule figure de 1 à 9 pour :

* chaque ligne,
* chaque colonne,
* chaque bloc de 3x3 interne.

## Comment vérifier qu'une grille Sudoku est valide?

### On va se faire un outil pour varifier que chaque contenu (ligne, colonne et bloc) contient toutes les valeurs de 1 à 9 mais en unique exemplaire.

    @Test
    @Parameters(method = "arraysProvider")
    public void check_array_contains_anly_one_figure_between_1_to_9(int[] array, boolean result) {

        LOGGER.info("array = {}", Arrays.toString(array));
        ContainsValidator containsValidator = new ContainsValidator("test", 9);
        Arrays.stream(array).forEach(containsValidator::add);

        assertThat(containsValidator.nonValid()).isNotEqualTo(result);

    }
    
    
Voir les tests ([katas.sudoku.rules.ContainsValidatorShould](src/test/java/katas/sudoku/rules/ContainsValidatorShould.java))
    
 
Cette fonctionnalité est réalisée par [katas.sudoku.rules.ContainsValidator](src/main/java/katas/sudoku/rules/ContainsValidator.java)

* On crée un tableau d'entier pour chaque figure de 1 à 9, qui permettra de compter le nombre de chacune d'elle présente:


    this.valuesCounter = new int[gridSize];

* On initialise les compteurs à zéro:


    IntStream.range(0, 9).forEach(valueIndex -> valuesCounter[valueIndex] = 0);
    
* Pour chaque valeur ajoutée on incrémente son compteur:


    public void add(int value) {
        if (value >= 1 && value <= gridSize) {
            valuesCounter[value - 1]++;
        }
    }
        

* On peut verifier si un compteur de valeur ne vaut pas 1:


    public boolean nonValid() {
           return Arrays.stream(valuesCounter).anyMatch(valueCounter -> valueCounter != 1);
    }

### Pour valider la grille Sudoku il faut que les règles ci-dessus soient vérifiées.


    @Test
    @Parameters(method = "sudokuGridProvider")
    public void check_a_sudoku_grid(int[][] sudokuGrid, boolean result) {
        Arrays.stream(sudokuGrid).forEach(this::showSudokuRow);
        SudokuChecker sudokuChecker = new SudokuChecker(sudokuGrid);
        assertThat(sudokuChecker.checkSudoku()).isEqualTo(result);
    }
    
Voir les tests ([katas.sudoku.rules.SudokuCheckerShould](src/test/java/katas/sudoku/rules/SudokuCheckerShould.java)) 
   
* On crée un validateur de contenu pour les lignes et les colonnes:


    private ContainsValidator row;
    private ContainsValidator column;
    
     private void resetRowAndColumn() {
        row = new ContainsValidator("row", gridSize);
        column = new ContainsValidator("column", gridSize);
    }

* On crée un tableau de validateurs pour les blocs:


    private ContainsValidator[] blocks;
        
    private void resetBlocks() {

        blocks = IntStream.rangeClosed(1, blockNumber) //
                .mapToObj(blockNumber -> new ContainsValidator("block #" + blockNumber, gridSize)) //
                .toArray(ContainsValidator[]::new);
    }    

 
* On verifie la grille [katas.sudoku.rules.SudokuChecker](src/main/java/katas/sudoku/rules/SudokuChecker.java):


    public boolean checkSudoku() {
        resetRowAndColumn();
        resetBlocks();

        return IntStream.range(0, gridSize).allMatch(rowIndex -> {

            IntStream.range(0, gridSize).forEach(columnIndex -> {
                row.add(sudokuGrid[rowIndex][columnIndex]);
                column.add(sudokuGrid[columnIndex][rowIndex]);
                blocks[rowIndex % blockNumber].add(sudokuGrid[rowIndex][columnIndex]);
            });

            if (isEndOfBlock(rowIndex)) {
                if (oneBlockNonValid()) {
                    return false;
                }
                resetBlocks();
            }
            if (row.nonValid() || column.nonValid()) {
                return false;
            }
            resetRowAndColumn();
            return true;
        });

    }
    
On itère sur chacune des lignes en vérifier qu'elles sont valides.
Pour chaque ligne on itère sur chaque colonne.
On ajoute la valeur dans le validateur de lignes, de colonnes et de blocs. On sélectionne le bon bloc puisqu'il y en a trois par ligne.
Quand on arrive au bout des lignes composant les blocs (multiple de 3) on verifie si il y n'a pas un bloc non valide.


    private boolean isEndOfBlock(int rowIndex) {
       return (rowIndex + 1) % blockNumber == 0;
    }
       
    private boolean oneBlockNonValid() {
        return Arrays.stream(blocks).anyMatch(ContainsValidator::nonValid);
    }
    
A la fin des blocs on réinitialise les validateurs pour les blocs des lignes suivantes.

On vérfie si une ligne ou une colonne n'est pas invalide:

     if (row.nonValid() || column.nonValid()) {
        return false;
     }

On réinitialise les validateurs de lignes et de colonnes pour itérer.

Si on arrive au bout la grille est valide.

 