package it.polimi.se2018.server.model.dice_sachet;

/**
 * Classe che rappresenta nel gioco un sacchetto virtuale da cui estrarre virtualmente un dado alla volta.
 * La classe è la classe commissionatore del pattern factory usato per la creazione dei dadi.
 * @author Kevin Mato
 */
public class DiceSachet{

    private FactoryDice factory;

    public DiceSachet(){
        this.factory=new FactoryDice();
    }

    /**
     * Metodo che effettua l'estrazione di un nuovo dado richiamando la fabbrica di dadi.
     * @return reference del nuovo dado creato
     */
    public Dice getDiceFromSachet(){
        return factory.createDice();
    }

    /**
     * Metodo che reinserisce un dado nel sacchetto.
     * @param d reference del dado reinserito.
     */
    public void reput(Dice d){
        factory.reput(d);
    }

}
