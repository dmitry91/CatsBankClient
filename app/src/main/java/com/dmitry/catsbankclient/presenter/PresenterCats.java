package com.dmitry.catsbankclient.presenter;

import com.dmitry.catsbankclient.models.ModelCats;

public class PresenterCats {

    private ModelCats modelCats;

    public PresenterCats() {
    }

    public void setModelCats(ModelCats modelCats) {
        this.modelCats = modelCats;
    }

    public void getAllCats(){
        modelCats.getAllCats();
    }

    public void findCatOnListById(int idCat){
        modelCats.findCatOnListById(idCat);
    }

    public void updateCat(int id, String text, String path){
        modelCats.updateCat(id, text, path);
    }

    public void deleteCat(int idCat){
        modelCats.deleteCat(idCat);
    }

    public void saveCat(String text, String path){
        modelCats.saveCat(text, path);
    }
}
