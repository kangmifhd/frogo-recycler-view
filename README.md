![ScreenShoot Apps](docs/image/ss_banner.png?raw=true)

## About This Project
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-frogo--recycler--view-brightgreen.svg?style=flat-square)](https://android-arsenal.com/details/1/8205)
[![JitPack](https://jitpack.io/v/amirisback/frogo-recycler-view.svg?style=flat-square)](https://jitpack.io/#amirisback/frogo-recycler-view)
[![Medium Badge](https://img.shields.io/badge/-faisalamircs-black?style=flat-square&logo=Medium&logoColor=white&link=https://medium.com/@fiqryq)](https://faisalamircs.medium.com/tutorial-recyclerview-cuman-15-detik-dengan-frogorecyclerview-cocok-buat-prototype-untuk-client-ad03b1af907e)
- Available on Google Dev Library [Click Here](https://devlibrary.withgoogle.com/products/android/repos/amirisback-frogo-recycler-view)
- RecyclerView No Adapter (Adapter Has Been Handled)
- RecyclerView No Adapter Using ViewBinding Adapter
- RecyclerView Multi-View-Type (Stable - Multi ViewHolder)
- Elegant call using injector()
- Shimmer Effect
- Empty View Effect
- Nested Recycler View
- Progress Recycler View

## Screen Shoot Apps

|Nested RecyclerView |   Frogo Shimmer              |   Frogo Multi View    | Simple Empty View |
|:------------------:|:----------------------------:|:---------------------:|:-----------------:|
|<img width="200px" height="360px" src="docs/image/ss_nested_simple.png"> | <img width="200px" height="360px" src="docs/image/sample_shimmer.gif"> | <img width="200px" height="360px" src="docs/image/ss_multi-view.png"> | <img width="200px" height="360px" src="docs/image/ss_empty.png"> |

## Version Release
This Is Latest Release

    $version_release = 3.8.5

What's New??

    * Update Build Gradle to 7.0.1 *
    * Update Frogo Android UI Kit *
    * Enhance Performance *

## Download this project

### Step 1. Add the JitPack repository to your build file (build.gradle : Project)
    
    Add it in your root build.gradle at the end of repositories:
    
    	allprojects {
    		repositories {
    			...
    			maven { url 'https://jitpack.io' }
    		}
    	}
      
### Step 2. Add the dependency (build.gradle : Module)
    
    dependencies {
            // library frogo-recycler-view
            implementation 'com.github.amirisback:frogo-recycler-view:3.8.5'
    }

### Step 3. Create xml view

    <com.frogobox.recycler.widget.FrogoRecyclerView
        android:id="@+id/frogo_recycler_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

### Step 4. Setup requirement No Adapter (You can choose 1 of the 4 options below)

#### <Option 1> Kotlin Injector (R class)

    private fun setupFrogoRecyclerView() {

        val adapterCallback = object : IFrogoViewAdapter<ExampleModel> {
            override fun setupInitComponent(view: View, data: ExampleModel) {
                // Init component content item recyclerview
                view.findViewById<TextView>(R.id.tv_example_item).text = data.name
            }

            override fun onItemClicked(data: ExampleModel) {
                // setup item clicked on frogo recycler view
                showToast(data.name)
            }

            override fun onItemLongClicked(data: ExampleModel) {
                // setup item long clicked on frogo recycler view
                showToast(data.name)
            }
        }

        binding.frogoRecyclerView
            .injector<ExampleModel>()
            .addData(listData())
            .addCustomView(R.layout.frogo_rv_list_type_1)
            .addEmptyView(null)
            .addCallback(adapterCallback)
            .createLayoutLinearVertical(false)
            .build()
    }

#### <Option 2> Kotlin Injector (ViewBinding) Can't use emptyView
    private fun setupFrogoRecyclerBinding() {

        val adapterCallback = object : IFrogoBindingAdapter<ExampleModel, FrogoRvListType1Binding> {
            override fun setupInitComponent(binding: FrogoRvListType1Binding, data: ExampleModel) {
                binding.frogoRvListType1TvTitle.text = data.name
                val context = binding.root.context
            }

            override fun setViewBinding(parent: ViewGroup): FrogoRvListType1Binding {
                return FrogoRvListType1Binding.inflate(LayoutInflater.from(parent.context), parent, false)
            }

            override fun onItemClicked(data: ExampleModel) {
                // setup item clicked on frogo recycler view
                showToast(data.name)
            }

            override fun onItemLongClicked(data: ExampleModel) {
                // setup item long clicked on frogo recycler view
                showToast(data.name)
            }
        }

        binding.frogoRecyclerView.injectorBinding<ExampleModel, FrogoRvListType1Binding>()
            .addData(listDataBinding())
            .addCallback(adapterCallback)
            .createLayoutLinearVertical(false)
            .build()

    }

#### <Option 3> Kotlin Builder (R class)
    private fun setupRvBuilder() {
        binding.frogoRecyclerView.builder(object : IFrogoBuilderRv<ExampleModel>{
            override fun setupData(): List<ExampleModel> {
                // Setup data FrogoRecyclerView
                return listData()
            }

            override fun setupCustomView(): Int {
                // Setup Custom View
                return R.layout.frogo_rv_list_type_1
            }

            override fun setupEmptyView(): Int? {
                // Setup Empty View
                return null
            }

            override fun setupLayoutManager(context: Context): RecyclerView.LayoutManager {
                // Setup Layout Manager of FrogoRecyclerView
                return FrogoLayoutManager.linearLayoutVertical(context)
            }

            override fun setupInitComponent(view: View, data: ExampleModel) {
                // Init component content item recyclerview
                view.findViewById<TextView>(R.id.frogo_rv_list_type_1_tv_title).text = data.name
            }

            override fun onItemClicked(data: ExampleModel) {
                // setup item clicked on frogo recycler view
                showToast(data.name)
            }

            override fun onItemLongClicked(data: ExampleModel) {
                // setup item long clicked on frogo recycler view
                showToast(data.name)
            }
        })
    }

#### <Option 4> Kotlin Builder (ViewBinding)
    private fun setupRvBuilderBinding() {
        binding.frogoRecyclerView.builderBinding(object :
            IFrogoBuilderRvBinding<ExampleModel, FrogoRvListType1Binding> {
            override fun setupData(): List<ExampleModel> {
                // Setup data FrogoRecyclerView
                return dataBuilderBinding
            }

            override fun setupLayoutManager(context: Context): RecyclerView.LayoutManager {
                // Setup Layout Manager of FrogoRecyclerView
                return FrogoLayoutManager.linearLayoutVertical(context)
            }

            override fun setupInitComponent(binding: FrogoRvListType1Binding, data: ExampleModel) {
                binding.frogoRvListType1TvTitle.text = data.name
            }

            override fun setViewBinding(parent: ViewGroup): FrogoRvListType1Binding {
                return FrogoRvListType1Binding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            }

            override fun onItemClicked(data: ExampleModel) {
                // setup item clicked on frogo recycler view
                showToast(data.name)
            }

            override fun onItemLongClicked(data: ExampleModel) {
                // setup item long clicked on frogo recycler view
                showToast(data.name)
            }

        })
    }

## Tutorial
- FrogoRecyclerView [Click Here](https://github.com/amirisback/frogo-recycler-view/blob/master/docs/tutorial/FrogoRecyclerView.md)
- FrogoShimmerRecyclerView [Click Here](https://github.com/amirisback/frogo-recycler-view/blob/master/docs/tutorial/FrogoShimmerRecyclerView.md)
- FrogoNestedRecyclerView [Click Here](https://github.com/amirisback/frogo-recycler-view/blob/master/docs/tutorial/FrogoNestedRecyclerView.md)
- FrogoProgressRecyclerView [Click Here](https://github.com/amirisback/frogo-recycler-view/blob/master/docs/tutorial/FrogoProgressRecyclerView.md)
- FrogoRecyclerView Multi Holder [Click Here](https://github.com/amirisback/frogo-recycler-view/blob/master/docs/tutorial/FrogoRecyclerViewMultiHolder.md)

## Library Helper
- frogo-android-ui-kit [Click Here](https://github.com/frogobox/frogo-android-ui-kit)
- frogo-log [Click Here](https://github.com/amirisback/frogo-log)
- consumable-code-news-api [Click Here](https://github.com/amirisback/consumable-code-news-api)

##  Alert

### Cautions :
    >> under Version 3.0.1
    - Please implement library [frogo-ui-kit](https://github.com/amirisback/frogo-ui-kit) in your project
    - We separating resource ui for better maintenance

    >> on Version 3.2.0
    - If you use version under 3.2.0 you must pay attenttion to package import
    - Please re-import package
    - Package name [base, parent, boilerplate] updated to core

    >> on Version 3.3.0 up
    - If you use version under 3.3.0 you must pay attenttion to package import
    - Please re-import package
    - Package name [base, parent, boilerplate] updated to core
    - No more package name [viewrclass, viewbinding, viewshimmer] all in core

### Update :
    >> on Version 3.2.0
    from -> import com.frogobox.recycler.boilerplate.viewrclass.FrogoViewAdapterCallback
    to -> import com.frogobox.recycler.core.viewrclass.FrogoViewAdapterCallback

    >> on Version 3.3.0 up
    from -> import com.frogobox.recycler.core.viewrclass.FrogoViewAdapterCallback
    to -> import com.frogobox.recycler.core.IFrogoViewAdapter

    >> on Version 3.3.0 up
    from -> FrogoViewAdapterCallback
    to -> IFrogoViewAdapter

### Wiki
- Development Planning [Click Here](https://github.com/amirisback/frogo-recycler-view/wiki/Development-Planning)

## Colaborator
Very open to anyone, I'll write your name under this, please contribute by sending an email to me

- Mail To faisalamircs@gmail.com
- Subject : Github _ [Github-Username-Account] _ [Language] _ [Repository-Name]
- Example : Github_amirisback_kotlin_admob-helper-implementation

Name Of Contribute
- Muhammad Faisal Amir
- Waiting List
- Waiting List

Waiting for your contribute

## Insipiration
- Nested-RecyclerView ( [Jeffrey Liu](https://github.com/jeffreyliu8) - [Project](https://github.com/jeffreyliu8/Nested-RecyclerView) )

## Attention !!!
- Please enjoy and don't forget fork and give a star
- Don't Forget Follow My Github Account

![ScreenShoot Apps](docs/image/mad_score.png?raw=true)
