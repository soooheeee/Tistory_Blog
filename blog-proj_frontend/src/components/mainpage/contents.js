import React from 'react'
import Postbox from '../postbox'
import "../../styles/Contents.css"

const Contents = () => {
  return (
   <div class="big_postbox">
    <h5 className="contentname">Food</h5>
      <ul>
        <li class="dual_postbox"><Postbox/></li>
        <li class="dual_postbox"><Postbox/></li>
        <li class="dual_postbox"><Postbox/></li>
        <li class="dual_postbox"><Postbox/></li>
    </ul>
   </div>
  )
}

export default Contents