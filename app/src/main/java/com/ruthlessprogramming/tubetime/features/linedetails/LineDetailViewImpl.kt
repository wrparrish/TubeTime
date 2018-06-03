package com.ruthlessprogramming.tubetime.features.linedetails

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import com.ruthlessprogramming.data.StopPointDataModel
import com.ruthlessprogramming.data.toDataModel
import com.ruthlessprogramming.data.toDomain
import com.ruthlessprogramming.domain.StopPoint
import com.ruthlessprogramming.tubetime.R
import com.ruthlessprogramming.tubetime.di.Injector
import com.ruthlessprogramming.tubetime.features.linedetails.epoxy.LineController
import com.ruthlessprogramming.tubetime.features.linedetails.state.LineWithLocation
import kotlinx.android.synthetic.main.screen_line_detail.rvLineDetails

class LineDetailViewImpl : MvpFragment<LineDetailView, LineDetailPresenter>(), LineDetailView {

    private  val controller: LineController by lazy {  LineController(resources) }
    override fun render(combinedState: LineWithLocation) {
        controller.setData(combinedState)
        val lineState = combinedState.lineDetailsState
        if (!lineState.hasFetchedDetails && !lineState.isLoading){
            presenter.fetchDetailsForLine(passedLineId)
        }
    }


    override fun createPresenter(): LineDetailPresenter = Injector.get().linePresenter


    private lateinit var passedStopPoint: StopPoint
    private lateinit var passedLineId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        passedStopPoint = arguments?.getParcelable<StopPointDataModel>(keyPassedStop)?.toDomain() ?:
                throw UnsupportedOperationException("You must provide a selected stop to the line details screen")
        passedLineId = arguments?.getString(keyPassedLineId, "") ?:
                throw UnsupportedOperationException("You must provide a line id to the line details screen")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.screen_line_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecView()
        presenter.initPassedArgs(passedLineId, passedStopPoint)
    }

    private fun setupRecView() {
        rvLineDetails.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = controller.adapter
        }
    }

    companion object {
        const val keyPassedStop = "passedStopKey"
        const val keyPassedLineId = "passedLineIdKey"

        fun newInstance(stop: StopPoint, lineId: String): LineDetailViewImpl =
            LineDetailViewImpl().apply {
                arguments = Bundle().apply {
                    putParcelable(keyPassedStop, stop.toDataModel())
                    putString(keyPassedLineId, lineId)
                }
            }


    }


}