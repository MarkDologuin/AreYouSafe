#include <iostream>
using namespace std;

int main (int argc, char* argv[]) {
    int fatalities, injuries;
    cout<<"Killed: ";
    cin>>fatalities;
    cout<<"Injured: ";
    cin>>injuries;
    if (fatalities==0 && injuries <= 250) {
        cout<<"Safe"<<endl;
    }
    else if (fatalities==0 && injuries >250 && injuries <=750) {
        cout<<"Passable"<<endl;
    }
    else if (fatalities!=0)  {
        cout<<"Unsafe"<<endl;
    }
    return 0;
}