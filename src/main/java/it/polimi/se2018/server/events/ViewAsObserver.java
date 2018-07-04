package it.polimi.se2018.server.events;

import it.polimi.se2018.server.events.responses.*;

  public  interface ViewAsObserver {

        void update(SuccessSimpleMove mex);
        void update(SuccessColor mex);
        void update(SuccessValue mex);
        void update(SuccessActivation mex);
        void update(SuccessActivationFinalized mex);
        void update(ErrorSelection mex);
        void update(ErrorActivation mex);
        void update(ErrorSomethingNotGood mex);
        void update(UpdateM mex);
        void update(AskPlayer mex);

        void update(TimeIsUp mex);

        void update(ErrorSelectionUtensil mex);

        void update(Freeze mex);
        void update(DisconnectPlayer mex);

        void update(IgnoreMex mex);
}
