#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <cstdlib>
#include <time.h>
#include <algorithm>

using namespace std;

//VARIABILI GLOBALI
/*
char tabellone [17][17]= {
    {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
    {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
    {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
    {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
    {' ','c',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
    {' ','a',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
    {' ','c','o','s','a',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
    {' ','c',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
    {'m','i','r','a','g','g','i','o',' ',' ',' ',' ',' ',' ',' ',' ',' '},
    {'e','a',' ',' ',' ',' ',' ','l',' ',' ',' ',' ',' ',' ',' ',' ',' '},
    {'n',' ',' ',' ',' ',' ',' ','t',' ',' ',' ',' ',' ',' ',' ',' ',' '},
    {'t','E',' ',' ',' ',' ',' ','r',' ',' ',' ',' ',' ',' ',' ',' ',' '},
    {'e',' ',' ',' ',' ',' ',' ','e',' ',' ',' ',' ',' ',' ',' ',' ',' '},
    {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
    {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
    {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
    {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
};
*/

char tabellone [17][17]= {
    {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
    {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
    {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
    {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
    {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
    {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
    {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
    {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
    {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
    {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
    {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
    {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
    {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
    {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
    {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
    {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
    {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
};

char tabellone_backup [17][17];


vector<char> alfabeto = {'a','a','a','a','a','a','a','a','a','a','a','a','b','b','b','b','c','c','c','c','c','c','c',
                         'd','d','d','d','e','e','e','e','e','e','e','e','e','e','e','e','f','f','f','f','g','g','g',
                         'g','h','h','i','i','i','i','i','i','i','i','i','i','i','i','l','l','l','l','l','l','m','m',
                         'm','m','m','m','n','n','n','n','n','n','o','o','o','o','o','o','o','o','o','o','o','o','p',
                         'p','p','p','q','q','r','r','r','r','r','r','r','s','s','s','s','s','s','s','t','t','t','t',
                         't','t','t','u','u','u','u','v','v','v','v','z','z','*','*'};
bool ultimo_turno;

//STRUCT

struct jolly
{
    bool jolly;
    int cont;
    int usati;
} jolly;

struct vettore_bool
{
    bool vero_falso;
    string tempbisx; //Lettere inserite non presenti sul tabellone
};

struct controllolettere{
    vector<char> nuovo; //Lettere random rimanenti dopo aver scritto la parola
    bool giusto; //True se il giocatore ha le lettere per scrivere la parola
    string con_jolly;
};

struct giocatore
{   string nome; //nome del giocatore
    int punteggio=0; //punteggio attuale del giocatore
    vector<char> lettere; //al giocatore
}primo, secondo, terzo, quarto;

struct suggerimento_
{
    bool vero;
    int riga;
    int colonna;
    bool verso;

};

//FUNZIONI


//FUNZIONE BACKUP

//Permette di scrivere una parola sul tabellone potendo eventualmente tornare alla configurazione precedente.
void backup (bool versione)
{
    //se la parola è sbagliata e la si vuole cancellare, versione=true

    if (versione){
        for (int i=0; i<17; i++) {
            for (int e=0; e<17; e++)
            {
                tabellone[i][e]= tabellone_backup[i][e];
            }
        }
    }

    //se si vuole salvare il tabellone all'inizio del turno

    else if (!versione)
    {
        for (int i=0; i<17; i++) {
            for (int e=0; e<17; e++)
            {
                tabellone_backup[i][e]= tabellone[i][e];
            }
        }
    }
}

//CONTROLLO SE LE LETTERE SONO GIA SUL TABELLONE E SE LA PAROLA ENTRA

vettore_bool controgrig(string temp, int riga, int colonna, bool verso, bool vuoto)
{
    int cont=0;
    vettore_bool x;

    //VERTICALE
    if (verso)
    {
        //colonna fissa, cambio riga
        //controllo che non si ecceda la griglia

        if (riga+temp.size()>17) {//cout << "La parola non entra" << endl;
            x.vero_falso=false; return x;}

        else
        {
            for (unsigned a=0; a<temp.size(); a++)
            {
                if (tabellone[riga+a][colonna]!=' '){
                    if (tabellone[riga+a][colonna]==temp[a] || tabellone[riga+a][colonna]==tolower(temp[a])) {cont++;}
                    else {x.vero_falso=false;
                        //cout << "Queste caselle sono gia' occupate" << endl;
                        return x;}}

                else {x.tempbisx.push_back(temp[a]);}

            }
        }
        if (x.vero_falso == true && vuoto == true) {return x;}
        if (x.vero_falso==true && cont>0) {return x;}
        else if (x.vero_falso==true && cont==0) {x.vero_falso=false; return x;}
    }

    //ORIZZONTALE
    if (!verso)
    {
        //riga fissa, cambio colonna
        //controllo che non si ecceda la griglia

        if (colonna+temp.size()>17) {//cout << "La parola non entra" << endl;
            x.vero_falso=false; return x;}

        else
        {
            for (unsigned b=0; b<temp.size(); b++)
            {
                if (tabellone[riga][colonna+b]!=' '){
                    if (tabellone[riga][colonna+b]==temp[b] || tabellone[riga][colonna+b]==toupper(temp[b])) {cont++; }
                    else {x.vero_falso=false; //cout << "Queste caselle sono gia' occupate" << endl;
                        return x;}}
                else {x.tempbisx.push_back(temp[b]);}

            }
        }
        if (x.vero_falso == true && vuoto == true) {return x;}
        if (x.vero_falso==true && cont>0) {return x;}
        else if (x.vero_falso==true && cont==0) {x.vero_falso=false; return x;}
    }
    return x;
}

//ASSEGNAZIONE RANDOM DELLE LETTERE

void assegnalettere(int lettere, int giocatore) {
    srand(time(0)); //Crea lista di numeri che cambiano in base a time(0)
    char lettera;
    while (lettere>0){
        if (alfabeto.size()>0){
            int random=rand()%alfabeto.size();
            lettera = alfabeto[random];

            if (lettera!=' '){
                switch(giocatore){
                case 1:
                    primo.lettere.push_back(lettera); break;
                case 2:
                    secondo.lettere.push_back(lettera); break;
                case 3:
                    terzo.lettere.push_back(lettera); break;
                case 4:
                    quarto.lettere.push_back(lettera); break;
                }
                alfabeto.erase(alfabeto.begin() + random);
                lettere--;}}

        else {
            cout << "Le lettere da assegnare sono finite; questo e' l'ultimo turno." << endl;
            ultimo_turno=true;
            lettere=0;
        }


    }

}

//CONTROLLO LETTERE CHE HA IL GIOCATORE

/* Scorre le lettere della parola che si vuole scrivere sul tabellone e controlla che
* ognuna corrisponda ad almeno una lettera tra quelle disponibili per il giocatore
* alla fine del controllo restituisce le nuove lettere del giocatore
*/
controllolettere controlloletteregiocatore(string &tempbis, int numerogiocatore){
    vector<char> lettere;
    switch (numerogiocatore){
    case 1: lettere=primo.lettere; break;
    case 2: lettere=secondo.lettere; break;
    case 3: lettere=terzo.lettere; break;
    case 4: lettere=quarto.lettere; break;
    }

    controllolettere nuovo;
    int jollyusatix = jolly.usati;
    unsigned b=0;

    for (unsigned a=0; a<tempbis.size()-1; a++){

        while (b<lettere.size()){

            if (tempbis[a]==lettere[b]){

                if (a==tempbis.size()-1) {lettere.erase(lettere.begin()+b); nuovo.giusto=true; nuovo.nuovo=lettere; return nuovo;}
                else {lettere.erase(lettere.begin()+b); a++; b=0;}
            } else if (tempbis[a]!=lettere[b] && b==lettere.size()-1) {

                if (jolly.usati==0){nuovo.giusto=false; return nuovo;}

                if (jollyusatix>0){
                    if (a==tempbis.size()-1) {jollyusatix--; nuovo.giusto=true; nuovo.nuovo=lettere; return nuovo;}
                    else {jollyusatix--; a++; b=0;}
                }
            }
            else {b++;}}
    }
    return nuovo;
}

//STAMPA MATRICE

void stampa(){

    cout << "    0 1 2 3 4 5 6 7 8 9 1 1 1 1 1 1 1" << endl;
    cout << "                        0 1 2 3 4 5 6" << endl;
    cout << "    # # # # # # # # # # # # # # # # #" << endl;
    for (int r=0; r<17; r++){
        if (r<10) {cout << r << "  #";}
        else {cout << r << " #";}
        for (int c=0; c<16; c++) {
            cout << tabellone[r][c] << " ";
        }
        cout << " #" << endl;
    }
    cout << "    # # # # # # # # # # # # # # # # #" << endl << endl;
}


//RICERCA DICOTOMICA DIZIONARIO

bool controdizionario (int first, int last, string &parola, const vector<string>& dizio)
{
    int mediano;

    if ((first+last)%2==0) {
        mediano = (first + last)/2;}
    else {
        mediano = (first+last)/2 + 1;
    }

    if (last==first+1) { //Se first e last sono consecutivi controlla le due parole
        if (parola==dizio[first] || parola==dizio[last]) {return true;}
        else {return false;}
    }



    if (parola==dizio[mediano]){return true;}
    if (parola!=dizio[mediano])
    {
        if (parola < dizio[mediano])
        {
            last = mediano;
            return controdizionario(first, last, parola,  dizio);
        }
        if (parola > dizio[mediano])
        {
            first = mediano;
            return controdizionario(first, last, parola, dizio);
        }
    }
    return false;
}

//CONTROLLO DIZ

bool parola_diz(string &parola){
    if (parola.size()<3) {return true;}
    for (unsigned i=0; i<parola.size(); i++) {parola[i]=tolower(parola[i]);}
    vector<string> dizio;
    string str;
    ifstream dizionario("C:/Users/Utente/Desktop/Scarabeo/660000_parole_italiane.txt");     // Apri file
    char c;
    while (dizionario >> noskipws >> c){
        if (c!='\n') {str.push_back(c);}
        if (c=='\n') {dizio.push_back(str); str.erase();}
    }

    sort(dizio.begin(), dizio.end());

    if (controdizionario(0, dizio.size(), parola, dizio) == true){
        //cout << "La parola '" << parola << "' e' nel dizionario." << endl << endl;
        return true;}
    else {//cout << "La parola '" << parola << "' non e' nel dizionario." << endl;
        return false;
    }
}

//INCROCI

/* Richiamata una volta che una parola è stata scritta sul tabellone per osservare gli incroci che essa crea e deciderne la validità.
 *  Se non sono libere viene salvata la parola formata dalle lettere adiacenti e dalla parola scritta dal giocatore.
 *  La parola inizia a riga,colonna e finisce a
 *  VERTICALE: riga + lunghezza -1, colonna
 *  ORIZZONTALE: riga, colonna + lunghezza - 1
*/
bool contro_incroci (int riga, int colonna, bool verso, int lunghezza) {
    string str;
    str.clear();
    //verticale
    if (verso) {int contsup=0; int continf=0;

        //sup
        if (riga!=0) {

            for (int a=1; a<riga+1; a++)
            {
                if (tabellone[riga-a][colonna]!=' ') {contsup++;}
                else if (tabellone[riga-a][colonna]==' ') {a=riga+1;}
            }
        }

        //inf
        if (riga + lunghezza < 18)
        {
            for (int b=1; b<18-lunghezza-riga; b++)
            {
                if (tabellone[riga+lunghezza-1+b][colonna]!=' ') {continf++;}
                else if (tabellone[riga+lunghezza-1+b][colonna]==' ') {b=18-lunghezza-riga;}
            }
        }

        if (contsup>0 && continf>0) {
            for (int c=0; c<contsup+lunghezza+continf; c++) {str.push_back(tabellone[riga-contsup+c][colonna]);}
        }
        else if (contsup>0 && continf==0) {
            for (int d=0; d<lunghezza+contsup; d++) {str.push_back(tabellone[riga-contsup+d][colonna]);}
        }
        else if (contsup==0 && continf>0) {
            for (int e=0; e<lunghezza+continf; e++) {str.push_back(tabellone[riga+e][colonna]);}
        }
    }

    else {
        int contsx=0; int contdx=0;
        //sx
        if (colonna!=0) {
            for (int f=1; f<colonna+1; f++) {
                if (tabellone[riga][colonna-f]!=' ') {contsx++;}
                else if (tabellone[riga][colonna-f]) {f=colonna+1;}
            }
        }

        //dx
        if (colonna + lunghezza < 18) {
            for (int g=1; g<18-lunghezza-colonna; g++) {
                if (tabellone[riga][colonna+lunghezza-1+g]!=' ') {contdx++;}
                else if (tabellone[riga][colonna+lunghezza-1+g]==' ') {g=18-lunghezza-colonna;}
            }
        }
        if (contsx>0 && contdx>0) {
            for (int h=0; h<lunghezza+contsx+contdx; h++) {str.push_back(tabellone[riga][colonna-contsx+h]);}
        }
        else if (contsx>0 && contdx==0) {
            for (int i=0; i<lunghezza+contsx; i++) {str.push_back(tabellone[riga][colonna-contsx+i]);}
        }
        else if (contsx==0 && contdx>0) {
            for (int l=0; l<lunghezza+contdx; l++) {str.push_back(tabellone[riga][colonna+l]);}
        }
    }

    if (str.size()>0) {if (!parola_diz(str)) {return false;}

        else {str.clear();}}

    //VERTICALE INCROCI ORIZZONTALI

    if (verso) {
        //per ogni lettera
        for (int m=0; m<lunghezza; m++) {
            int contsxx=0; int contdxx=0; str.clear();
            //sx
            if (colonna!=0) {
                for (int n=1; n<colonna+1; n++) {
                    if (tabellone[riga+m][colonna-n]!=' ') {contsxx++;}
                    else if (tabellone[riga+m][colonna-n]) {n=colonna+1;}
                }
            }

            //dx
            if (colonna + lunghezza < 18) {
                for (int o=1; o<18-lunghezza-colonna; o++) {
                    if (tabellone[riga+m][colonna+o]!=' ') {contdxx++;}
                    else if (tabellone[riga+m][colonna+o]==' ') {o=18-lunghezza-colonna;}
                }
            }
            if (contsxx>0 && contdxx>0) {
                for (int p=0; p<1+contsxx+contdxx; p++) {str.push_back(tabellone[riga+m][colonna-contsxx+p]);}
            }
            else if (contsxx>0 && contdxx==0) {
                for (int q=0; q<1+contsxx; q++) {str.push_back(tabellone[riga+m][colonna-contsxx+q]);}
            }
            else if (contsxx==0 && contdxx>0) {
                for (int r=0; r<1+contdxx; r++) {str.push_back(tabellone[riga+m][colonna+r]);}
            }
            if (str.size()>0) {if (!parola_diz(str)) {return false;}

                else {str.clear();}}
        }
    }

    else { //ORIZZONTALE INCROCI VERTICALI
        for (int s=0; s<lunghezza; s++) {int contsupx=0; int continfx=0; str.clear();

            //sup
            if (riga!=0) {

                for (int t=1; t<riga+1; t++)
                {
                    if (tabellone[riga-t][colonna+s]!=' ') {contsupx++;}
                    else if (tabellone[riga-t][colonna+s]==' ') {t=riga+1;}
                }
            }

            //inf

            if (riga + lunghezza < 18)
            {
                for (int u=1; u<18-lunghezza-riga; u++)
                {
                    if (tabellone[riga+u][colonna+s]!=' ') {continfx++;}
                    else if (tabellone[riga+u][colonna+s]==' ') {u=18-1-riga;}
                }
            }

            if (contsupx>0 && continfx>0) {
                for (int v=0; v<1+contsupx+continfx; v++) {str.push_back(tabellone[riga-contsupx+v][colonna+s]);}
            }
            else if (contsupx>0 && continfx==0) {
                for (int w=0; w<1+contsupx; w++) {str.push_back(tabellone[riga-contsupx+w][colonna+s]);}
            }
            else if (contsupx==0 && continfx>0) {
                for (int x=0; x<1+continfx; x++) {str.push_back(tabellone[riga+x][colonna+s]);}
            }

            if (str.size()>0) {if (!parola_diz(str)) {return false;}

                else {str.clear();}}
        }
    }

    return true;}

//CONTEGGIO PUNTI

int conteggiopunti (string& par, int posizioneriga, int posizionecolonna, bool verso) {


    int punti = 0;

    vector<int> punteggitemporanei;

    for (unsigned i=0; i<par.size(); i++) {
        tolower(par[i]); //Rendo minuscola la lettera

        if (par[i]=='a' || par[i]=='c' || par[i]=='e' || par[i]=='i'|| par[i]=='o' || par[i]=='r' || par[i]=='s' || par[i]=='t')
        {punteggitemporanei.push_back(1);}
        else if (par[i]=='l' || par[i]=='m' || par[i]=='n') {punteggitemporanei.push_back(2);}
        else if (par[i]=='p'){punteggitemporanei.push_back(3);}
        else if (par[i]=='b' || par[i]=='d' || par[i]=='f' || par[i]=='g' || par[i]=='u' || par[i]=='v')
        {punteggitemporanei.push_back(4);}
        else if (par[i]=='h' || par[i]=='z') {punteggitemporanei.push_back(8);}
        else if (par[i]=='q') {punteggitemporanei.push_back(10);}
    }

    int score[17][17] = {
        {4,0,0,0,2,0,0,0,4,0,0,0,2,0,0,0,4},
        {0,3,0,0,0,0,1,0,0,0,1,0,0,0,0,3,0},
        {0,0,3,0,0,0,0,2,0,2,0,0,0,0,3,0,0},
        {0,0,0,3,0,0,0,0,2,0,0,0,0,3,0,0,0},
        {2,0,0,0,3,0,0,0,0,0,0,0,3,0,0,0,0},
        {0,0,0,0,0,3,0,0,0,0,0,3,0,0,0,0,0},
        {0,1,0,0,0,0,1,0,0,0,1,0,0,0,0,1,0},
        {0,0,2,0,0,0,0,2,0,2,0,0,0,0,2,0,0},
        {4,0,0,2,0,0,0,0,0,0,0,0,0,2,0,0,4},
        {0,0,2,0,0,0,0,2,0,2,0,0,0,0,2,0,0},
        {0,1,0,0,0,0,1,0,0,0,1,0,0,0,0,1,0},
        {0,0,0,0,0,3,0,0,0,0,0,3,0,0,0,0,0},
        {2,0,0,0,3,0,0,0,0,0,0,0,3,0,0,0,0},
        {0,0,0,3,0,0,0,0,2,0,0,0,0,3,0,0,0},
        {0,0,3,0,0,0,0,2,0,2,0,0,0,0,3,0,0},
        {0,3,0,0,0,0,1,0,0,0,1,0,0,0,0,3,0},
        {4,0,0,0,2,0,0,0,4,0,0,0,2,0,0,0,4},
    };

    if (verso==true){ //verso=true è verticale
        for (unsigned i=0; i<punteggitemporanei.size(); i++){
            switch (score[posizioneriga+i][posizionecolonna]){
            case (1): punteggitemporanei[i]=punteggitemporanei[i]*3; break;
            case (2): punteggitemporanei[i]=punteggitemporanei[i]*2; break;
            }
        }
    }

    if (verso==false){ //verso=false è orizzontale
        for (unsigned i=0; i<punteggitemporanei.size(); i++){
            switch (score[posizioneriga][posizionecolonna+i]){
            case (1): punteggitemporanei[i]=punteggitemporanei[i]*3; break;
            case (2): punteggitemporanei[i]=punteggitemporanei[i]*2; break;
            }
        }
    }


    for (unsigned i=0; i<punteggitemporanei.size(); i++) {
        punti+=punteggitemporanei[i];
    }

    if (verso==true) { //verso=true è verticale
        for (unsigned i=0; i<punteggitemporanei.size(); i++) {
            switch (score[posizioneriga+i][posizionecolonna]){
            case (3): punti=punti*2; break;
            case (4): punti=punti*3; break;
            }
        }
    }
    if (verso==false) { //verso=false è orizzontale
        for (unsigned i=0; i<punteggitemporanei.size(); i++) {
            switch (score[posizioneriga][posizionecolonna+i]){
            case (3): punti=punti*2; break;
            case (4): punti=punti*3; break;
            }
        }
    }

    switch (par.size()) {
    case (6): punti+=10; break;
    case(7): punti+=30; break;
    case(8): punti+=50; break;
    }

    if (par=="scarabeo" || par=="scarabei") {punti+=100;}

    if (!jolly.jolly) {return(punti+10);}
    else {return punti;}
}

//CONTROLLO PER SUGGERIMENTO

//Controlla tutte le caselle sulla riga e colonna della lettere del tabellone considerata
suggerimento_ controllo_per_suggerimento (string &parola, int riga, int colonna, int numerogioc)
{  suggerimento_ prova; prova.vero=false;
    //VERTICALE
    for (int a=0; a<17; a++){
        vettore_bool cont;
        cont=controgrig(parola, a, colonna, true, false);

        if (cont.vero_falso){
            controllolettere n=controlloletteregiocatore(cont.tempbisx, numerogioc);

            if (n.giusto){
                backup(false);

                for (unsigned i=0; i<parola.size(); i++) {tabellone[a+i][colonna]=parola[i];}

                if (contro_incroci(a, colonna, true, parola.size())) {
                    backup(true); prova.riga=a; prova.colonna=colonna; prova.vero=true; prova.verso=true; return prova;}
                else {backup(true);}
            }
        }
    }

    //ORIZZONTALE
    for (int b=0; b<17; b++){
        vettore_bool cont;
        cont=controgrig(parola, riga, b, false, false);

        if (cont.vero_falso){
            backup(false);

            for (unsigned i=0; i<parola.size(); i++){tabellone[riga][b+i]=parola[i];}

            if (contro_incroci(riga, b, false, parola.size())) {
                backup(true); prova.riga=riga; prova.colonna=b; prova.vero=true; prova.verso=false; return prova;}
            else {backup(true);
            }
        }
    }
    return prova;
}

//CONTROLLO COMPLETO DELLA PAROLA

controllolettere controllocompleto (string &parola, int riga, int colonna, bool verso, int numerogioc, bool vuoto){

    string temporaneabis; controllolettere fine;

    //Verifico che la parola appartiene al dizionario
    if (parola_diz(parola)){

        //Controllo se entra
        //controllogriglia
        vettore_bool controllogriglia;
        controllogriglia.vero_falso=false;
        controllogriglia=controgrig(parola, riga, colonna, verso, vuoto);

        //Controlla che la parola possa essere scritta sul tabellone e che incroci almeno una lettera già presente su esso
        if (controllogriglia.vero_falso) {temporaneabis=controllogriglia.tempbisx;

            //Controllo letteregiocatore
            //Controllo se il giocatore possiede effettivamente le lettere per scrivere la parola passata in input
            fine=controlloletteregiocatore(temporaneabis, numerogioc);

            if (fine.giusto) {//cout << "Lettere valide" << endl << "le tue nuove lettere disponibili sarebbero:" << endl; for(int i=0; i<fine.nuovo.size(); i++){cout<< fine.nuovo[i];}
                fine.con_jolly=parola;
                if (verso) {for (unsigned a=0; a<parola.size(); a++) {if (tabellone[riga+a][colonna]>='A' && 'Z'>=tabellone[riga+a][colonna]){fine.con_jolly[a]=tabellone[riga+a][colonna];}}}
                else if (!verso) {for (unsigned a=0; a<parola.size(); a++) {if (tabellone[riga][colonna+a]>='A' && 'Z'>=tabellone[riga+a][colonna]){fine.con_jolly[a]=tabellone[riga+a][colonna];}}
                    return fine;}}

            //controllo letteregiocatore
            else {cout << "Lettere non valide. Puoi usare solo le lettere che ti sono state assegnate." << endl; fine.giusto=false; return fine;}}


        //controllo se entra
        else {cout<< "Non e' possibile inserire questa parola nel tabellone." << endl; fine.giusto=false; return fine;} }



    //controllo dizionario
    else {cout << "Questa parola non e' nel dizionario." << endl; fine.giusto=false; return fine;}
    return fine;
    //Se tutti i controlli risultano positivi, la funzione restituisce le nuove lettere del giocatore
}

//CONTEGGIO PUNTI DUE

int conteggiopunti_due (string& par) //conteggio dei punti che non guarda al tabellone, usato per il suggerimento
{
    int punti = 0;

    vector<int> punteggitemporanei;

    for (unsigned i=0; i<par.size(); i++) {
        tolower(par[i]); //Rendo minuscola la lettera

        if (par[i]=='a' || par[i]=='c' || par[i]=='e' || par[i]=='i'|| par[i]=='o' || par[i]=='r' || par[i]=='s' || par[i]=='t')
        {punti=punti+1;}
        else if (par[i]=='l' || par[i]=='m' || par[i]=='n') {punti=punti+2;}
        else if (par[i]=='p'){punti=punti+3;}
        else if (par[i]=='b' || par[i]=='d' || par[i]=='f' || par[i]=='g' || par[i]=='u' || par[i]=='v')
        {punti=punti+4;}
        else if (par[i]=='h' || par[i]=='z') {punti=punti+8;}
        else if (par[i]=='q') {punti=punti+10;}
    }

    switch (par.size()) {
    case (6): punti+=10; break;
    case(7): punti+=30; break;
    case(8): punti+=50; break;
    }

    if (par=="scarabeo" || par=="scarabei") {punti+=100;}


    if (!jolly.jolly) {return(punti+10);}
    else {return punti;}
}

//CODICE

//"Crittografiamo" le lettere del giocatore per ottenere un identificatore dei loro anagrammi
int codice (char c) {

    c=tolower(c);
    switch (c){

    case 'a': return 2; break;
    case 'b': return 3; break;
    case 'c': return 5; break;
    case 'd': return 7; break;
    case 'e': return 11; break;
    case 'f': return 13; break;
    case 'g': return 17; break;
    case 'h': return 19; break;
    case 'i': return 23; break;
    case 'l': return 29; break;
    case 'm': return 31; break;
    case 'n': return 37; break;
    case 'o': return 41; break;
    case 'p': return 43; break;
    case 'q': return 47; break;
    case 'r': return 53; break;
    case 's': return 59; break;
    case 't': return 61; break;
    case 'u': return 67; break;
    case 'v': return 71; break;
    case 'z': return 73; break;
    case '*': return 1; break;
    case 'j': return 79; break;
    case 'k': return 83; break;
    case 'w': return 89; break;
    case 'x': return 97; break;
    case 'y': return 101; break;
    }
    return 1;}

//FIND NUMBER

//Algoritmo di ricerca dicotomico per trovare la parola all'interno del vettore<long long> &cont
int findnumber(int first, int last, long long nuova, vector<long long> &cont, int codicex)
{

    int mediano;

    if ((first+last)%2==0) {
        mediano = (first + last)/2;}
    else {
        mediano = (first+last)/2 + 1;
    }

    if (last==first+1) { //Se first e last sono consecutivi controlla le due parole
        if (nuova==cont[first]*codicex || nuova==cont[last]*codicex) {return 0;}
        else {return 1;}
    }



    if (nuova==cont[mediano]*codicex){return 0;}
    if (nuova!=cont[mediano]*codicex)
    {
        if (nuova < cont[mediano]*codicex)
        {
            last = mediano;
            return findnumber(first, last, nuova,  cont, codicex);
        }
        if (nuova > cont[mediano]*codicex)
        {
            first = mediano;
            return findnumber(first, last, nuova, cont, codicex);
        }
    }
    return 1;
}

//FUN FACT

void fun_fact()
{srand(time(0));
    cout << "-----------------------------------------------------------" << endl;
    cout << "Nel frattempo! Lo sapevi che ... ";
    int random=rand()%14;
    switch(random){
    case 0: cout << "lo Scarabeo e' stato inventato nel 1993 da un architetto di New York, Alfred Mosher Butts?" << endl; break;
    case 1: cout << "il primo nome dello Scarabeo era Lexico, abbreviazione di Lexicon?" << endl; break;
    case 2: cout << "il suo creatore decise la frequenza delle lettere nello Scarabeo basandosi su una pagina del New York Times?" << endl; break;
    case 3: cout << "esiste un'edizione Braille dello Scarabeo?" << endl; break;
    case 4: cout << "ogni ora vengono inziate 30000 partite di Scarabeo?" << endl; break;
    case 5: cout << "tra le celebrita' appassionate dello Scarabeo ci sono Sting, John Travolta, Tom Cruise e la Regina Elisabetta?" << endl; break;
    case 6: cout << "nell'edizione inglese dello Scarabeo si possono fare 1782 punti con una parola, Oxyphenbutazone?" << endl; break;
    case 7: cout << "nel 1985, il tenente comandante Waghorn e il suo sottoposto Gill giocarono a Scarabeo per cinque giorni consecutivi, perche' intrappolati in un crepaccio in Antartide?" << endl; break;
    case 8: cout << "a Leicester, Inghilterra, un bambino di cinque anni ha telefonato alla polizia perche' la sorella barava a Scarabeo?" << endl; break;
    case 9: cout << "esiste una rubrica in un quotidiano thailandese completamente dedicata allo Scarabeo?" << endl; break;
    case 10: cout << "la mossa con cui un giocatore riesce a usare tutte le sue tessere per formare una parola viene chiamata Bingo?" << endl; break;
    case 11: cout << "se si disponessero per terra tutte le tessere dello Scarabeo mai prodotte la fila si estenderebbe per piu' di 80000 chilometri?" << endl; break;
    case 12: cout << "esiste una versione dello Scarabeo chiamata Clabbers in cui le lettere possono essere messe in qualunque ordine, purche' formino l'anagramma di una parola?" << endl; break;
    case 13: cout << "piu' della meta' delle famiglie della Gran Bretagna possiede un'edizione dello Scarabeo?" << endl; break;
    case 14: cout << "lo Scarabeo e' disponibile in piu' di cinquanta lingue, incluso il gallese, l'irlandese e il Klingon?" << endl; break;
    }
    cout << "-----------------------------------------------------------" << endl;
}

//SUGGERIMENTO

void suggerimento(int giocatore, string &parola){
    vector<char> lettere; vector<long long> cont;
    long long temp=1; long long cont_dizio=1; long long prima;
    int riga; int colonna; int punteggi=0; bool verso; int fun=0;
    string str; str.clear();

    switch(giocatore)
    {
    case 1: for (unsigned x=0; x<primo.lettere.size(); x++) {lettere.push_back(primo.lettere[x]);} break;
    case 2: for (unsigned x=0; x<secondo.lettere.size(); x++){lettere.push_back(secondo.lettere[x]);} break;
    case 3: for (unsigned x=0; x<terzo.lettere.size(); x++){lettere.push_back(terzo.lettere[x]);} break;
    case 4: for (unsigned x=0; x<quarto.lettere.size(); x++){lettere.push_back(quarto.lettere[x]);} break;

    }

    int lett=lettere.size();
    for (unsigned e=0; e<lettere.size(); e++)  {temp=temp*codice(lettere[e]);} //temp=temp*cod;
    cont.push_back(temp); prima=temp;  //8+1

    temp=1;

    //Viene creata una lista di codici che identificano tutte le combinazioni delle lettere del giocatore.

    //tutte le lettere - 1 + tabellone
    if (lett>3) {for (unsigned f=0; f<lettere.size(); f++) {temp=prima/(codice(lettere[f])); cont.push_back(temp);} lett--;} //7+1


    if (lett>3){for (unsigned g=0; g<lettere.size(); g++) {for (unsigned h=g+1; h<lettere.size(); h++) {temp=prima/(codice(lettere[g])); temp=temp/(codice(lettere[h])); cont.push_back(temp);} }lett--;} //6+1


    if (lett>3) {for (unsigned i=0; i<lettere.size(); i++){for (unsigned l=i+1; l<lettere.size(); l++){for (unsigned m=l+1; m<lettere.size(); m++) {temp=prima/(codice(lettere[i])); temp=temp/(codice(lettere[l])); temp=temp/(codice(lettere[m])); cont.push_back(temp);} }}lett--;} //5+1


    if (lett>3) {for (unsigned n=0; n<lettere.size(); n++) {for (unsigned o=n+1; o<lettere.size(); o++){  for (unsigned p=o+1; p<lettere.size(); p++){for (unsigned q=p+1; q<lettere.size(); q++) {temp=prima/(codice(lettere[n])); temp=temp/(codice(lettere[o])); temp=temp/(codice(lettere[p])); temp=temp/(codice(lettere[q])); cont.push_back(temp);} }}}lett--;} //4+1


    if (lett>3) {for (unsigned r=0; r<lettere.size(); r++){for (unsigned s=r+1; s<lettere.size(); s++){for (unsigned t=s+1; t<lettere.size(); t++){for (unsigned u=t+1; u<lettere.size(); u++){for (unsigned v=u+1; v<lettere.size(); v++){temp=prima/(codice(lettere[r])); temp=temp/(codice(lettere[s])); temp=temp/(codice(lettere[t])); temp=temp/(codice(lettere[u])); temp=temp/(codice(lettere[v])); cont.push_back(temp);}}}}}lett--;}


    for (unsigned w=0; w<cont.size(); w++) {for (unsigned v=0; v<cont.size(); v++){if (cont[w]==cont[v] && v!=w) {cont.erase(cont.begin()+w);}}}

    sort(cont.begin(), cont.end());



    cout << "Cerchiamo una parola adatta ..." << endl; fun_fact();
    for (unsigned a=0; a<17; a++)
    { for (unsigned b=0; b<17; b++)
        { if (tabellone[a][b]!=' ')
            { fun++;
                //cout << tabellone[a][b] << endl;
                ifstream dizionario("C:/Users/Utente/Desktop/Scarabeo/660000_parole_italiane.txt");     // Apri file
                char c;
                while (dizionario >> noskipws >> c){
                    if (c!='\n') {
                        cont_dizio=cont_dizio*codice(c);
                        str.push_back(c);}
                    if (c=='\n') {
                        if (conteggiopunti_due(str)>punteggi)

                            //Se la parola risulta avere un punteggio maggiore delle precedenti il codice (prodotto di numeri primi) ad essa corrispondente
                            //viene passato alla funzione findnumber() insieme al codice corrispondente alla lettera del tabellone identificata precedentemente.

                            //        inizio   fine         numero parola diz vettore numeri  numero lettera tabellone
                        {
                            if (findnumber(0, cont.size()-1, cont_dizio,        cont,        codice(tolower(tabellone[a][b])))==0) {
                                suggerimento_ contr= controllo_per_suggerimento(str, a, b, giocatore);
                                if (contr.vero) {
                                    //cout << "Forse ..." << endl;
                                    parola=str; riga=contr.riga; colonna=contr.colonna; verso=contr.verso;
                                    punteggi=conteggiopunti_due(str); //cout << parola << endl;
                                }}
                        } str.clear(); cont_dizio=1;
                    }}
                if (fun==10){fun_fact(); fun=0;}}}}

    stampa();

    if (parola == "?") {cout << "Non abbiamo suggerimenti per parole di lunghezza maggiore di tre. Ma non preoccuparti, puoi scrivere qualunque sigla da due o tre lettere!" << endl; return;}
    cout << "Il nostro suggerimento e' la parola '" << parola << "', con cui puoi totalizzare " << punteggi << " punti." << endl <<
            "La puoi inserire in "; if (verso){cout << "verticale in riga ";} else {cout << "orizzontale in riga ";} cout << riga << " e colonna " << colonna << endl;
    return;}

//VINCITORE

//Viene chiamata nel caso in cui le lettere da distribuire e già distribuite sono finite o nel caso in cui il tabellone è pieno.
//Verifica quale dei giocatori ha ottenuto il punteggio maggiore e se si è verificato nel caso un pareggio.

void vincitore (int numerogiocatori){
    cout << "Il gioco e' finito!" << endl;
    cout << "Complimenti! ";
    switch(numerogiocatori){
    case 2: if (primo.punteggio>secondo.punteggio) {cout << primo.nome << " hai vinto!"; return;}
        else if (primo.punteggio==secondo.punteggio) {cout << "Avete pareggiato!"; return;}
        else {cout << secondo.nome << " hai vinto!"; return;}
    case 3: if (primo.punteggio>secondo.punteggio) {
            if (primo.punteggio>terzo.punteggio){
                //vince primo
                cout << primo.nome << " hai vinto!"; return;
            }
            else if (terzo.punteggio>primo.punteggio) {
                //vince terzo
                cout << terzo.nome << " hai vinto!"; return;
            }
        }
        else if (secondo.punteggio>primo.punteggio){
            if (secondo.punteggio>terzo.punteggio){
                //vince secondo
                cout << secondo.nome << " hai vinto!"; return;
            }
            else if (terzo.punteggio>secondo.punteggio){
                //vince terzo
                cout << terzo.nome << " hai vinto!"; return;
            }
        }

        if (primo.punteggio==secondo.punteggio)
        {if (primo.punteggio==terzo.punteggio){cout << "Avete pareggiato!"; return;}
            else if (primo.punteggio>terzo.punteggio){cout << primo.nome << " e " << secondo.nome << " siete primi a parimerito" << endl; return;}
            else {cout << terzo.nome << " hai vinto!"; return; }}

        else {if (primo.punteggio==terzo.punteggio)
             {if (primo.punteggio>secondo.punteggio){cout << primo.nome << " e " << terzo.nome << " siete primi a parimerito" << endl;return;}

                else {cout << secondo.nome << " hai vinto!"; return;}}

            if (secondo.punteggio==terzo.punteggio){
                if (secondo.punteggio>primo.punteggio){cout << secondo.nome << " e " << terzo.nome << " siete primi a parimerito" << endl; return;}
                else {cout << primo.nome << " hai vinto!"; return;}
            }
        }
    case 4:
        if (primo.punteggio>secondo.punteggio){
            if (primo.punteggio>terzo.punteggio) { cout << primo.nome << " hai vinto!"; return;}
            else if (primo.punteggio<terzo.punteggio) { cout << terzo.nome << " hai vinto!"; return;}
        }
        else {
            if (secondo.punteggio>terzo.punteggio) { cout << secondo.nome << " hai vinto!"; return;}
            else if (secondo.punteggio<terzo.punteggio) { cout << terzo.nome << " hai vinto!"; return;}
        }
    }
}

//TURNO

bool turno (int numerogiocatori, int prossimogiocatore, int tabellone_pieno, bool inizio)
{
    //CASO FINE DEL GIOCO
    //Controlla se non è più possibile inserire lettere nel tabellone e in tal caso fa terminare la partita.
    //Controlla che il giocatore abbia otto lettere e in caso contrario chiama la funzione assegnalettere().

    if (tabellone_pieno>=289) {vincitore(numerogiocatori); return true;}
    if (ultimo_turno) {tabellone_pieno=289;}


    int riga=-1; int colonna=-1; bool verso; string temporanea; jolly.jolly=false;
    jolly.cont=0;
    string temporaneabis;
    int punti;

    controllolettere parolaaccettata; bool controllocompletato=false; parolaaccettata.giusto=false;
    switch (prossimogiocatore) {
    case 1:
        cout << "----------------Turno di " << primo.nome << "----------------" << endl;
        if (primo.lettere.size()<8 && !ultimo_turno) {assegnalettere(8-primo.lettere.size(), 1);}
        break;
    case 2:
        cout << "----------------Turno di " << secondo.nome << "----------------" << endl;
        if (secondo.lettere.size()<8 && !ultimo_turno) {assegnalettere(8-secondo.lettere.size(), 2);}
        break;
    case 3:
        cout << "----------------Turno di " << terzo.nome << "----------------" << endl;
        if (terzo.lettere.size()<8 && !ultimo_turno) {assegnalettere(8-terzo.lettere.size(), 3);}
        break;
    case 4:
        cout << "----------------Turno di " << quarto.nome << "----------------" << endl;
        if (quarto.lettere.size() <8 && !ultimo_turno) {assegnalettere(8-quarto.lettere.size(), 4);}
        break;

    }

    stampa();

    //Salva la disposizione corrente delle lettere sul tabellone  prima del controllo
    backup(false);

    while (!parolaaccettata.giusto) {  //Può uscire solo una volta che il giocatore avrà dato in input una parola valida
    jolly.cont = 0; jolly.jolly = false;
        riga=-1; colonna=-1;
        cout << "Ecco le tue lettere: ";
        switch (prossimogiocatore) {
        case 1:
            for (unsigned i=0; i<8; i++){cout << " " << primo.lettere[i] << " "; if (primo.lettere[i]=='*'){jolly.cont++;}}
            break;
        case 2:
            for (unsigned i=0; i<8; i++){cout << " " << secondo.lettere[i] << " "; if (secondo.lettere[i]=='*'){jolly.cont++;}}
            break;
        case 3:
            for (unsigned i=0; i<8; i++){cout << " " << terzo.lettere[i] << " "; if (terzo.lettere[i]=='*'){jolly.cont++;}}
            break;
        case 4:for (unsigned i=0; i<8; i++){cout << " " << quarto.lettere[i] << " "; if (quarto.lettere[i]=='*'){jolly.cont++;}}
            break;
        }

        cout << endl;

        //CASO JOLLY
        if (jolly.cont>0)
        {cout << "Ricorda, hai " << jolly.cont << " jolly. Puoi usarl"; if (jolly.cont==1) {cout << "o ";} else if (jolly.cont>1) {cout << "i ";} cout << "al posto di qualunque lettera." << endl;}
        //CASO JOLLY

        cout << "Scegli la parola. " << "Se hai bisogno di un suggerimento, scrivi '?'" << endl;
        cin >> temporanea;
        cout << endl;

        //SUGGERIMENTO
        if (temporanea=="?") {cout << "Controlliamo le tue lettere!" << endl;

            suggerimento(prossimogiocatore, temporanea);

            cout << "Scegli la parola: ";
            cin >> temporanea;
            cout << endl;}
        //SUGGERIMENTO
        cout << "Dove vuoi inserire la parola? Inserisci la riga: ";
        while (riga<0 || riga >16) {cin >> riga;}
        cout << "Inserisci la colonna: ";
        while (colonna<0 || colonna>16) {cin >> colonna;}
        cout << "Verticale o orizzontale? Scrivi 'v' per verticale oppure 'o' per orizzontale: ";
        char c=' ';
        while (c!='o'&& c!='v') {cin >> c;}
        if (c=='v') {verso=true;}
        else if (c=='o') {verso=false;}

        //CASO JOLLY
        string j;
        while (jolly.cont>0) {cout << "Vuoi usare un jolly? Ricorda, perderai 10 punti. Scrivi 'si' se vuoi usarlo, 'no' altrimenti" << endl;
            while (j!="si" && j!="no")  {cin>> j;
            if(j!="si" && j!="no") cout << "Scrivi la parola giusta!" << endl;
            }
            if (j=="si") {jolly.jolly=true; jolly.usati++; jolly.cont--;}
            else if (j=="no") {jolly.cont--;}
        }
        //CASO JOLLY

        //CONTROLLO PAROLA
        switch (prossimogiocatore) {
        case 1:
            parolaaccettata=controllocompleto(temporanea, riga, colonna, verso, 1, inizio);
            break;
        case 2:
            parolaaccettata=controllocompleto(temporanea, riga, colonna, verso, 2, inizio);
            break;
        case 3:
            parolaaccettata=controllocompleto(temporanea, riga, colonna, verso, 3, inizio);
            break;
        case 4:
            parolaaccettata=controllocompleto(temporanea, riga, colonna, verso, 4, inizio);
            break;

        }
        //CONTROLLO PAROLA
    }

    //SCRITTURA TEMPORANEA PAROLA
    for (unsigned i=0; i<temporanea.size(); i++)
    {
        if (verso) {if (tabellone[riga+i][colonna]==' ' || islower(tabellone[riga+i][colonna])) {tabellone[riga+i][colonna]=temporanea[i];}}
        else {if (tabellone[riga][colonna+i]==' ' || islower(tabellone[riga][colonna+i])){tabellone[riga][colonna+i]=temporanea[i];}}
    }
    //SCRITTURA TEMPORANEA PAROLA

    //CONTROLLO INCROCI
    if (contro_incroci(riga, colonna, verso, temporanea.size())){
    //CONTROLLO INCROCI


        //CASO JOLLY
        while (jolly.usati>0) {
            int rg=-1; int cl=-1;
            cout <<  "Dove vuoi usare il jolly? Inserisci la riga: ";
            while (rg<0 || rg>16){cin >> rg;}
            cout << "Inserisci la colonna: ";
            while (cl<0|| cl>16) {cin >> cl;}
            tabellone[rg][cl]=toupper(tabellone[rg][cl]);

            switch(prossimogiocatore){
            case 1: for (unsigned x=0; x<primo.lettere.size(); x++)
                {if (primo.lettere[x]=='*') {primo.lettere.erase(primo.lettere.begin() + x); x=primo.lettere.size();}} break;
            case 2:for (unsigned x=0; x<secondo.lettere.size(); x++) {if (secondo.lettere[x]=='*')
                    {secondo.lettere.erase(secondo.lettere.begin() + x); x=secondo.lettere.size();}} break;
            case 3: for (unsigned x=0; x<terzo.lettere.size(); x++) {if (terzo.lettere[x]=='*')
                    {terzo.lettere.erase(terzo.lettere.begin() + x); x=terzo.lettere.size();}} break;
            case 4: for (unsigned x=0; x<quarto.lettere.size(); x++) {if (quarto.lettere[x]=='*')
                    {quarto.lettere.erase(quarto.lettere.begin() + x); x=quarto.lettere.size();}} break;
            }

            jolly.usati--;
        }
        //CASO JOLLY


        //CASO RIPRESA JOLLY

        switch (prossimogiocatore) {


        case 1:
            primo.lettere=parolaaccettata.nuovo;

            for (unsigned a=0; a<17; a++) {
                for (unsigned b=0; b<17; b++)
                {
                    if (tabellone[a][b]>='A' && tabellone[a][b]<='Z') {char c=tolower(tabellone[a][b]);
                        for (unsigned g=0; g<primo.lettere.size(); g++) {if (primo.lettere[g]==c)
                            {cout << "Vuoi scambiare la tua " << primo.lettere[g] << " per il jolly sul tabellone? Scrivi 'si' se si altrimenti 'no'." << endl;
                                string ss;
                                cin >> ss;
                                if (ss=="si") {tabellone[a][b]=primo.lettere[g]; primo.lettere.erase(primo.lettere.begin() + g); primo.lettere.push_back('*');}
                            }
                        }
                    }
                }
            }
            break;

        case 2:
            secondo.lettere=parolaaccettata.nuovo;

            for (unsigned a=0; a<17; a++) {
                for (unsigned b=0; b<17; b++)
                {
                    if (isupper(tabellone[a][b])) {char c=tolower(tabellone[a][b]);
                        for (unsigned g=0; g<secondo.lettere.size(); g++) {if (secondo.lettere[g]==c)
                            {cout << "Vuoi scambiare la tua " << secondo.lettere[g] << " per il jolly sul tabellone? Scrivi si se si altrimenti no" << endl;
                                string ss;
                                cin >> ss;
                                if (ss=="si") {tabellone[a][b]=tolower(tabellone[a][b]); secondo.lettere.erase(secondo.lettere.begin() + g); secondo.lettere.push_back('*');}
                            }
                        }
                    }
                }
            }

            break;

        case 3:
            terzo.lettere=parolaaccettata.nuovo;

            for (unsigned a=0; a<17; a++) {
                for (unsigned b=0; b<17; b++)
                {
                    if (isupper(tabellone[a][b])) {char c=tolower(tabellone[a][b]);
                        for (unsigned g=0; g<terzo.lettere.size(); g++) {if (terzo.lettere[g]==c)
                            {cout << "Vuoi scambiare la tua " << terzo.lettere[g] << " per il jolly sul tabellone? Scrivi si se si altrimenti no" << endl;
                                string ss;
                                cin >> ss;
                                if (ss=="si") {tabellone[a][b]=tolower(tabellone[a][b]); terzo.lettere.erase(terzo.lettere.begin() + g); terzo.lettere.push_back('*');}
                            }
                        }
                    }
                }
            }

            break;
        case 4:

            quarto.lettere=parolaaccettata.nuovo;

            for (unsigned a=0; a<17; a++) {
                for (unsigned b=0; b<17; b++)
                {
                    if (isupper(tabellone[a][b])) {char c=tolower(tabellone[a][b]);
                        for (unsigned g=0; g<quarto.lettere.size(); g++) {if (quarto.lettere[g]==c)
                            {cout << "Vuoi scambiare la tua " << quarto.lettere[g] << " per il jolly sul tabellone? Scrivi si se si altrimenti no" << endl;
                                string ss;
                                cin >> ss;
                                if (ss=="si") {tabellone[a][b]=tolower(tabellone[a][b]); quarto.lettere.erase(quarto.lettere.begin() + g); quarto.lettere.push_back('*');}
                            }
                        }
                    }
                }
            }
            break;
        }
        //CASO RIPRESA JOLLY


        //PUNTI
        punti= conteggiopunti(temporanea, riga, colonna, verso); //Aggiorno i punteggi
        cout << "Questa parola vale " << punti << " punti" << endl;
        //PUNTI

        //la parola è giusta, tocca al prossimo giocatore

        stampa();


        tabellone_pieno=tabellone_pieno + temporanea.size();
        if (tabellone_pieno==289) {cout << "Il tabellone e' pieno!" << endl;}
        jolly.cont=0;
        jolly.jolly=false;
        jolly.usati=0;


        switch (numerogiocatori) {

        case 2:
            if (prossimogiocatore==1) {primo.punteggio+=punti; cout << "Il tuo nuovo punteggio e' " << primo.punteggio << endl;
                return turno(2,2, tabellone_pieno, false);}
            if (prossimogiocatore==2) {secondo.punteggio+=punti; cout << "Il tuo nuovo punteggio e' " << secondo.punteggio << endl;
                return turno(2,1, tabellone_pieno, false);}
            break;
        case 3:
            if (prossimogiocatore==1) {primo.punteggio+=punti; cout << "Il tuo nuovo punteggio e' " << primo.punteggio << endl;
                return turno(3,2, tabellone_pieno, false);}
            if (prossimogiocatore==2) {secondo.punteggio+=punti; cout << "Il tuo nuovo punteggio e' " << secondo.punteggio << endl;
                return turno(3,3, tabellone_pieno, false);}
            if (prossimogiocatore==3) {terzo.punteggio+=punti; cout << "Il tuo nuovo punteggio e' " << terzo.punteggio << endl;
                return turno(3,1, tabellone_pieno, false);}
            break;
        case 4:
            if (prossimogiocatore==1) {primo.punteggio+=punti; cout << "Il tuo nuovo punteggio e' " << primo.punteggio << endl;
                return turno(4,2, tabellone_pieno, false);}
            if (prossimogiocatore==2) {secondo.punteggio+=punti; cout << "Il tuo nuovo punteggio e' " << secondo.punteggio << endl;
                return turno(4,3, tabellone_pieno, false);}
            if (prossimogiocatore==3) {terzo.punteggio+=punti; cout << "Il tuo nuovo punteggio e' " << terzo.punteggio << endl;
                return turno(4,4, tabellone_pieno, false);}
            if (prossimogiocatore==4){ quarto.punteggio+=punti; cout << "Il tuo nuovo punteggio e' " << quarto.punteggio << endl;
                return turno(4,1, tabellone_pieno, false);}
            break;
        }
    }

    else {backup(true); cout << "Mi dispiace! La parola crea incroci non validi con le altre lettere del tabellone"<< endl;

        switch (numerogiocatori) {

        case 2:
            if (prossimogiocatore==1) {return turno(2,1, tabellone_pieno, inizio);}
            if (prossimogiocatore==2) {return turno(2,2, tabellone_pieno, inizio);}
            break;
        case 3:
            if (prossimogiocatore==1) {return turno(3,1, tabellone_pieno, inizio);}
            if (prossimogiocatore==2) {return turno(3,2, tabellone_pieno, inizio);}
            if (prossimogiocatore==3) {return turno(3,3, tabellone_pieno, inizio);}
            break;
        case 4:
            if (prossimogiocatore==1) {return turno(4,1, tabellone_pieno, inizio);}
            if (prossimogiocatore==2) {return turno(4,2, tabellone_pieno, inizio);}
            if (prossimogiocatore==3) {return turno(4,3, tabellone_pieno, inizio);}
            if (prossimogiocatore==4) {return turno(4,4, tabellone_pieno, inizio);}
            break;
        }
    }
    return false;
}

// MAIN

int main()
{
    char numGiocatori;
    string parola;

    cout << "SCARABEO" << endl << endl;


    //SCELTA GIOCATORI

    cout << "Inserisci il numero di giocatori (da 2 a 4): ";

    do{
        cin >> numGiocatori;
        if (numGiocatori!='2' && numGiocatori!='3' && numGiocatori!='4') {
            cout << "Il numero inserito non e' valido! Inserisci il numero di giocatori (da 2 a 4): ";
        }
    }while(numGiocatori!='2' && numGiocatori!='3' && numGiocatori!='4');


    //ASSEGNO LE LETTERE PER LA PRIMA VOLTA
    string str;

    switch (numGiocatori) {
    case '2':

        cout << "Scrivi il nome del primo giocatore e premi invio: ";
        cin >> str;
        primo.nome=str;
        assegnalettere(8,1);
        primo.punteggio=0;

        cout << "Scrivi il nome del secondo giocatore e premi invio: ";
        cin >> str;
        secondo.nome=str;
        assegnalettere(8, 2);
        secondo.punteggio=0;
        turno(2, 1, 0, true);
        break;

    case '3':

        cout << "Scrivi il nome del primo giocatore e premi invio: ";
        cin >> str;
        primo.nome=str;
        assegnalettere(8, 1);
        primo.punteggio=0;

        cout << "Scrivi il nome del secondo giocatore e premi invio: ";
        cin >> str;
        secondo.nome=str;
        assegnalettere(8, 2);
        secondo.punteggio=0;

        cout << "Scrivi il nome del terzo giocatore e premi invio: ";
        cin >> str;
        terzo.nome=str;
        assegnalettere(8, 3);
        terzo.punteggio=0;

        turno(3, 1, 0, true);

        break;
    case '4':

        cout << "Scrivi il nome del primo giocatore e premi invio: ";
        cin >> str;
        primo.nome=str;
        assegnalettere(8, 1);
        primo.punteggio=0;

        cout << "Scrivi il nome del secondo giocatore e premi invio: ";
        cin >> str;
        secondo.nome=str;
        assegnalettere(8, 2);
        secondo.punteggio=0;

        cout << "Scrivi il nome del terzo giocatore e premi invio: ";
        cin >> str;
        terzo.nome=str;
        assegnalettere(8, 3);
        terzo.punteggio=0;


        cout << "Scrivi il nome del quarto giocatore e premi invio: ";
        cin >> str;
        quarto.nome=str;
        assegnalettere(8, 4);
        quarto.punteggio=0;

        turno(4,1, 0, true);
        break;
    }



    return 0;
}
